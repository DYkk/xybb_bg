package com.wx.xybb.service.impl;

import com.wx.xybb.entity.SysUser;
import com.wx.xybb.mapper.SysUserMapper;
import com.wx.xybb.service.HomeService;
import com.wx.xybb.service.PermissionService;
import com.wx.xybb.vo.resp.HomeRespVO;
import com.wx.xybb.vo.resp.PermissionRespNodeVO;
import com.wx.xybb.vo.resp.UserInfoRespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: HomeServiceImpl
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PermissionService permissionService;
    @Override
    public HomeRespVO getHome(String userId) {
        HomeRespVO homeRespVO=new HomeRespVO();
        /**
         * mock 导航菜单数据后期直接从DB获取
         */
//        String home="[{\"children\":[{\"children\":[{\"children\":[{\"children\":[{\"children\":[],\"id\":\"6\",\"title\":\"五级类目5-6\",\"url\":\"string\"}],\"id\":\"5\",\"title\":\"四级类目4-5\",\"url\":\"string\"}],\"id\":\"4\",\"title\":\"三级类目3-4\",\"url\":\"string\"}],\"id\":\"3\",\"title\":\"二级类目2-3\",\"url\":\"string\"}],\"id\":\"1\",\"title\":\"类目1\",\"url\":\"string\"},{\"children\":[],\"id\":\"2\",\"title\":\"类目2\",\"url\":\"string\"}]";
//        String home="[\n" +
//                "    {\n" +
//                "        \"children\": [\n" +
//                "            {\n" +
//                "                \"children\": [],\n" +
//                "                \"id\": \"3\",\n" +
//                "                \"title\": \"菜单权限管理\",\n" +
//                "                \"url\": \"/index/menus\"\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"id\": \"1\",\n" +
//                "        \"title\": \"组织管理\",\n" +
//                "        \"url\": \"string\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"children\": [],\n" +
//                "        \"id\": \"2\",\n" +
//                "        \"title\": \"类目2\",\n" +
//                "        \"url\": \"string\"\n" +
//                "    }\n" +
//                "]";
        List<PermissionRespNodeVO> list=permissionService.permissionTreeList(userId);
        homeRespVO.setMenus(list);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        UserInfoRespVO vo=new UserInfoRespVO();
        if(sysUser!=null){
            BeanUtils.copyProperties(sysUser,vo);
            vo.setDeptName("迎学教育总公司");
        }
        homeRespVO.setUserInfoVO(vo);
        return homeRespVO;
    }
}
