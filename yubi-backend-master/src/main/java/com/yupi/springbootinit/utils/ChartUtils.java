package com.yupi.springbootinit.utils;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.ThrowUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChartUtils {

    /**
     * 提取生成的图表的Echarts配置的正则
     */
    static String GEN_CHART_REGEX = "\\{(?>[^{}]*(?:\\{[^{}]*}[^{}]*)*)}";
    public static final Pattern VALID_GEN_CHART_PATTERN = Pattern.compile(GEN_CHART_REGEX,Pattern.COMMENTS);

    public static String validEcharts(String preGenChart){
        Matcher matcher = VALID_GEN_CHART_PATTERN.matcher(preGenChart);
        ThrowUtils.throwIf(!matcher.find(), ErrorCode.SYSTEM_ERROR,"AI生成图表错误");
        return matcher.group();
    }
}
