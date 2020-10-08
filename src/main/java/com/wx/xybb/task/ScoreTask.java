package com.wx.xybb.task;

import com.wx.xybb.entity.WxEveryScore;
import com.wx.xybb.entity.WxTotalScore;
import com.wx.xybb.mapper.WxEveryScoreMapper;
import com.wx.xybb.mapper.WxTotalScoreMapper;
import com.wx.xybb.utils.WxUtils;
import com.wx.xybb.vo.resp.WxCourseTaskRespVO;
import com.wx.xybb.vo.resp.WxIdScoreTaskRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Shawshank King
 * @date 2020-08-10 - 16:56
 */

@Component
@EnableScheduling
public class ScoreTask {

    @Autowired
    private WxEveryScoreMapper wxEveryScoreMapper;
    @Autowired
    private WxTotalScoreMapper wxTotalScoreMapper;

    @Scheduled(cron = "0 30 2 * * ?")//每天上午2:30进行成绩排名
    public void scoreRankTask(){
        List<WxCourseTaskRespVO> wxCourseS = wxEveryScoreMapper.groupByCourse();
        for (WxCourseTaskRespVO wxCours : wxCourseS) {
            List<WxIdScoreTaskRespVO> wxIdScores = wxEveryScoreMapper.selectByCourseAndClass(wxCours.getCourse(), wxCours.getClassGrade());
            int i = 1;//用作排名的排序，从第一名开始
            int size = wxIdScores.size();//参与排名的人数
            for (WxIdScoreTaskRespVO wxIdScore : wxIdScores) {
                if (WxUtils.isNumericZidai(wxIdScore.getScore())){
                    WxEveryScore wxEveryScore = new WxEveryScore();
                    wxEveryScore.setId(wxIdScore.getId());
                    wxEveryScore.setScoreRank(i++);
                    wxEveryScore.setNumberPeople(size);
                    wxEveryScoreMapper.updateByIdSelective(wxEveryScore);
                }
            }
        }
        JidianRankTask();
    }

    public void JidianRankTask(){
        List<String> strings = wxTotalScoreMapper.groupByGrade();
        for (String string : strings) {
            List<Long> longs = wxTotalScoreMapper.selectByGrade(string);
            int i = 1;//用作排名的排序，从第一名开始
            int size = longs.size();//参与排名的人数
            for (Long aLong : longs) {
                WxTotalScore wxTotalScore = new WxTotalScore();
                wxTotalScore.setId(aLong);
                wxTotalScore.setJpaRank(i++);
                wxTotalScore.setNumberPeople(size);
                wxTotalScoreMapper.updateById(wxTotalScore);
            }
        }
    }



}
