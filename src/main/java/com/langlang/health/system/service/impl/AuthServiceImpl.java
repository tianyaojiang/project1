package com.langlang.health.system.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.langlang.health.system.entity.Permission;
import com.langlang.health.system.entity.PermissionVO;
import com.langlang.health.system.entity.Role;
import com.langlang.health.system.entity.RolePermissionKey;
import com.langlang.health.system.entity.RolePermissionVO;
import com.langlang.health.system.mapper.PermissionMapper;
import com.langlang.health.system.mapper.RoleMapper;
import com.langlang.health.system.mapper.RolePermissionMapper;
import com.langlang.health.system.service.AuthService;

/**
 * Created by tyj on 2018/08/14.
 */
@Service
@Slf4j
public  class AuthServiceImpl implements AuthService {
    
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public int addPermission(Permission permission) {
        return this.permissionMapper.insert(permission);
    }

    @Override
    public List<Permission> permList() {
        return this.permissionMapper.findAll();
    }

    @Override public int updatePerm(Permission permission) {
        return this.permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override public Permission getPermission(int id) {
        return this.permissionMapper.selectByPrimaryKey(id);
    }

    @Override public String delPermission(int id) {
        //查看该权限是否有子节点，如果有，先删除子节点
        List<Permission> childPerm = this.permissionMapper.findChildPerm(id);
        if(null != childPerm && childPerm.size()>0){
            return "删除失败，请您先删除该权限的子节点";
        }
        if(this.permissionMapper.deleteByPrimaryKey(id)>0){
            return "ok";
        }else{
            return "删除失败，请您稍后再试";
        }
    }

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return this.roleMapper.getRolesByUserId(userId);
    }

    @Override
    public List<Permission> getPermsByUserId(Integer userId) {
        return this.permissionMapper.getPermsByUserId(userId);
    }

    @Override
    public List<Role> roleList() {
        return this.roleMapper.findList();
    }

    @Override public List<PermissionVO> findPerms() {
        return this.permissionMapper.findPerms();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public String addRole(Role role, String permIds) {
        String[] arrays=permIds.split(",");
        StringBuilder basePerm = new StringBuilder();
        StringBuilder operPerm = new StringBuilder();
        for(String i:arrays) {
           Permission permission = permissionMapper.selectByPrimaryKey(Integer.parseInt(i));
           if ("基本权限".equals(permission.getIstype().trim())){
               basePerm.append(permission.getName()).append("，");
           }
           if ("操作权限".equals(permission.getIstype().trim())){
               operPerm.append(permission.getName()).append("，");
           }
        }
        basePerm.deleteCharAt(basePerm.length()-1);
        String bp=basePerm.toString().toLowerCase();
        role.setBasePermissions(bp);
        operPerm.deleteCharAt(operPerm.length()-1);
        String op=operPerm.toString().toLowerCase();
        role.setOperationPermissions(op);

        this.roleMapper.insert(role);
        int roleId=role.getId();
        log.debug("权限id =arrays="+arrays.toString());
        setRolePerms(roleId, arrays);
        return "ok";
    }

    @Override
    public List<RolePermissionVO> findRoleAndPerms(Integer id) {
        return this.roleMapper.findRoleAndPerms(id);
    }

    @Override
    public Role selectByPrimaryKey (Integer id) {
        return this.roleMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public String updateRole(Role role, String permIds) {
        int roleId=role.getId();
        String[] arrays=permIds.split(",");
        log.debug("权限id =arrays="+arrays.toString());
        //1，更新角色表数据；
        int num=this.roleMapper.updateByPrimaryKeySelective(role);
        if(num<1){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "操作失败";
        }
        //2，删除原角色权限；
        batchDelRolePerms(roleId);
        //3，添加新的角色权限数据；
        setRolePerms(roleId, arrays);
        return "ok";
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public String delRole(int id) {
        //1.删除角色对应的权限
        batchDelRolePerms(id);
        //2.删除角色
        int num=this.roleMapper.deleteByPrimaryKey(id);
        if(num<1){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "操作失败";
        }
        return "ok";
    }

    @Override
    public List<Role> getRoles() {
        //TODO 根据部门和权限等级限制角色显示
        return this.roleMapper.getRoles();
    }

    @Override
    public List<Permission> findPermsByRoleId(Integer id) {
        return this.permissionMapper.findPermsByRole(id);
    }

    /**
     * 批量删除角色权限中间表数据
     * @param roleId
     */
    private void batchDelRolePerms(int roleId) {
        List<RolePermissionKey> rpks=this.rolePermissionMapper.findByRole(roleId);
        if(null!=rpks && rpks.size()>0){
            for (RolePermissionKey rpk : rpks) {
                this.rolePermissionMapper.deleteByPrimaryKey(rpk);
            }
        }
    }

    /**
     * 给当前角色设置权限
     * @param roleId
     * @param arrays
     */
    private void setRolePerms(int roleId, String[] arrays) {
        for (String permid : arrays) {
            RolePermissionKey rpk=new RolePermissionKey();
            rpk.setRoleId(roleId);
            rpk.setPermitId(Integer.valueOf(permid));
            this.rolePermissionMapper.insert(rpk);
        }
    }
}
