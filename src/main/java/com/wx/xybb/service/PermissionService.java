package com.wx.xybb.service;

import com.wx.xybb.entity.SysPermission;
import com.wx.xybb.vo.req.PermissionAddReqVO;
import com.wx.xybb.vo.req.PermissionUpdateReqVO;
import com.wx.xybb.vo.resp.PermissionRespNodeVO;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: PermissionService
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
public interface PermissionService {
    List<SysPermission> selectAll();
    List<PermissionRespNodeVO> selectAllMenuByTree();
    SysPermission addPermission(PermissionAddReqVO vo);
    List<PermissionRespNodeVO> permissionTreeList(String userId);
    List<PermissionRespNodeVO> selectAllTree();
    void updatePermission(PermissionUpdateReqVO vo);
    void deletedPermission(String permissionId);
    Set<String> getPermissionsByUserId(String userId);
    List<SysPermission> getPermission(String userId);
}
