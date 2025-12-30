package com.ruoyi.common.utils.pdf;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 题目PDF生成工具类
 *
 * @author ruoyi
 */
public class PdfQuestionUtils {
    private static final Logger log = LoggerFactory.getLogger(PdfQuestionUtils.class);

    // 页面边距 (点)
    private static final float PAGE_MARGIN_LEFT = 56f;   // ~20mm
    private static final float PAGE_MARGIN_RIGHT = 56f;  // ~20mm
    private static final float PAGE_MARGIN_TOP = 56f;    // ~20mm
    private static final float PAGE_MARGIN_BOTTOM = 56f; // ~20mm

    // 字体大小
    private static final float TITLE_FONT_SIZE = 16f;
    private static final float QUESTION_TYPE_FONT_SIZE = 14f;
    private static final float QUESTION_CONTENT_FONT_SIZE = 12f;
    private static final float QUESTION_OPTIONS_FONT_SIZE = 12f;
    private static final float QUESTION_ANSWER_FONT_SIZE = 12f;

    // 题型分类映射
    private static final Map<String, String> QUESTION_TYPE_MAP = new HashMap<>();

    static {
        QUESTION_TYPE_MAP.put("计算题", "三、计算题（请写出计算过程）");
        QUESTION_TYPE_MAP.put("计算", "三、计算题（请写出计算过程）");
        QUESTION_TYPE_MAP.put("选择题", "一、选择题（每题只有一个正确答案）");
        QUESTION_TYPE_MAP.put("选择", "一、选择题（每题只有一个正确答案）");
        QUESTION_TYPE_MAP.put("填空题", "二、填空题（请在横线上填写正确答案）");
        QUESTION_TYPE_MAP.put("填空", "二、填空题（请在横线上填写正确答案）");
        QUESTION_TYPE_MAP.put("应用题", "四、应用题（请详细解答并写出过程）");
        QUESTION_TYPE_MAP.put("应用", "四、应用题（请详细解答并写出过程）");
    }

    // 字体
    private static PdfFont chineseFont;

    static {
        try {
            // 使用系统默认字体支持中文
            chineseFont = PdfFontFactory.createFont();
        } catch (Exception e) {
            log.error("加载默认字体失败", e);
        }
    }

    /**
     * 根据大模型返回的结果生成PDF试卷
     *
     * @param modelResult 大模型返回的JSON结果
     * @param paperTitle  试卷标题
     * @return 生成的PDF文件路径
     * @throws IOException IO异常
     */
    public static String generateQuestionPaper(String modelResult, String paperTitle) throws IOException {
        long startTime = System.currentTimeMillis();

        // 解析JSON数据
        JSONObject resultJson;
        try {
            resultJson = JSON.parseObject(modelResult);
        } catch (Exception e) {
            log.error("解析JSON数据失败: {}", modelResult, e);
            throw new IOException("解析JSON数据失败: " + e.getMessage());
        }

        JSONArray questions = resultJson.getJSONArray("questions");

        if (questions == null || questions.isEmpty()) {
            throw new IOException("题目数据为空");
        }
        // 创建文件路径
        String fileName = "paper_" + IdUtils.fastUUID() + ".pdf";
        String uploadDir = RuoYiConfig.getProfile() + "/paper";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String filePath = uploadDir + "/" + fileName;
        File file = new File(filePath);
        // 初始化PDF文档
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDoc);
        // 设置页面边距
        document.setMargins(PAGE_MARGIN_TOP, PAGE_MARGIN_RIGHT, PAGE_MARGIN_BOTTOM, PAGE_MARGIN_LEFT);
        try {
            // 添加试卷标题
            addPaperTitle(document, paperTitle);
            // 添加题目
            addQuestions(document, questions);
        } catch (Exception e) {
            log.error("生成PDF内容失败", e);
            throw new IOException("生成PDF内容失败: " + e.getMessage());
        } finally {
            // 关闭文档
            try {
                document.close();
            } catch (Exception e) {
                log.error("关闭PDF文档失败", e);
            }
        }

        long endTime = System.currentTimeMillis();
        log.info("生成PDF试卷耗时: {}ms，题目数量: {}", (endTime - startTime), questions.size());

        return FileUploadUtils.getPathFileName(uploadDir, fileName);
    }

    /**
     * 添加试卷标题
     *
     * @param document   PDF文档
     * @param paperTitle 试卷标题
     */
    private static void addPaperTitle(Document document, String paperTitle) {
        if (chineseFont == null) {
            Paragraph title = new Paragraph(paperTitle)
                    .setFontSize(TITLE_FONT_SIZE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f);
            document.add(title);
        } else {
            Paragraph title = new Paragraph(paperTitle)
                    .setFont(chineseFont)
                    .setFontSize(TITLE_FONT_SIZE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f);
            document.add(title);
        }

        // 添加日期
        if (chineseFont == null) {
            Paragraph date = new Paragraph("生成时间: " + DateUtils.parseDateToStr("yyyy年MM月dd日", new Date()))
                    .setFontSize(QUESTION_CONTENT_FONT_SIZE)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginBottom(20f);
            document.add(date);
        } else {
            Paragraph date = new Paragraph("生成时间: " + DateUtils.parseDateToStr("yyyy年MM月dd日", new Date()))
                    .setFont(chineseFont)
                    .setFontSize(QUESTION_CONTENT_FONT_SIZE)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginBottom(20f);
            document.add(date);
        }
    }

    /**
     * 添加题目
     *
     * @param document  PDF文档
     * @param questions 题目数组
     */
    private static void addQuestions(Document document, JSONArray questions) {
        String currentType = "";
        int questionNumber = 1;

        for (int i = 0; i < questions.size(); i++) {
            JSONObject question = questions.getJSONObject(i);
            String type = question.getString("type");

            // 如果题型发生变化，添加题型标题
            if (!type.equals(currentType)) {
                currentType = type;
                questionNumber = 1;

                // 添加分页符（除第一个题型外）
                if (i > 0) {
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }

                // 添加题型标题
                String typeTitleText = QUESTION_TYPE_MAP.getOrDefault(type, type + "：");
                if (chineseFont == null) {
                    Paragraph typeTitle = new Paragraph(typeTitleText)
                            .setFontSize(QUESTION_TYPE_FONT_SIZE)
                            .setBold()
                            .setMarginTop(20f)
                            .setMarginBottom(15f);
                    document.add(typeTitle);
                } else {
                    Paragraph typeTitle = new Paragraph(typeTitleText)
                            .setFont(chineseFont)
                            .setFontSize(QUESTION_TYPE_FONT_SIZE)
                            .setBold()
                            .setMarginTop(20f)
                            .setMarginBottom(15f);
                    document.add(typeTitle);
                }
            }

            // 添加题目内容
            addQuestionContent(document, question, questionNumber++, type);
        }
    }

    /**
     * 添加题目内容
     *
     * @param document PDF文档
     * @param question 题目对象
     * @param number   题号
     * @param type     题型
     */
    private static void addQuestionContent(Document document, JSONObject question, int number, String type) {
        // 题目内容
        String content = question.getString("content");
        if (StringUtils.isEmpty(content)) {
            content = "题目内容为空";
        }

        if (chineseFont == null) {
            Paragraph contentPara = new Paragraph(number + ". " + content)
                    .setFontSize(QUESTION_CONTENT_FONT_SIZE)
                    .setMarginBottom(8f);
            document.add(contentPara);
        } else {
            Paragraph contentPara = new Paragraph(number + ". " + content)
                    .setFont(chineseFont)
                    .setFontSize(QUESTION_CONTENT_FONT_SIZE)
                    .setMarginBottom(8f);
            document.add(contentPara);
        }

        // 选择题选项
        if ("选择题".equals(type) || "选择".equals(type)) {
            JSONArray options = question.getJSONArray("options");
            if (options != null && !options.isEmpty()) {
                // 创建选项表格
                Table table = new Table(UnitValue.createPercentArray(new float[]{1, 10}));
                table.setWidth(UnitValue.createPercentValue(100));
                table.setBorder(Border.NO_BORDER);
                table.setMarginBottom(8f);

                for (int i = 0; i < options.size(); i++) {
                    String optionLabel = getOptionLabel(i);
                    String optionText = options.getString(i);

                    if (StringUtils.isEmpty(optionText)) {
                        optionText = "选项内容为空";
                    }

                    if (chineseFont == null) {
                        table.addCell(new Paragraph(optionLabel + ".").setFontSize(QUESTION_OPTIONS_FONT_SIZE)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setMargin(1f));
                        table.addCell(new Paragraph(optionText).setFontSize(QUESTION_OPTIONS_FONT_SIZE)
                                .setMargin(1f));
                    } else {
                        table.addCell(new Paragraph(optionLabel + ".").setFont(chineseFont)
                                .setFontSize(QUESTION_OPTIONS_FONT_SIZE)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setMargin(1f));
                        table.addCell(new Paragraph(optionText).setFont(chineseFont)
                                .setFontSize(QUESTION_OPTIONS_FONT_SIZE)
                                .setMargin(1f));
                    }
                }

                document.add(table);
            }
        }

        // 答案和解析
        Object answerObj = question.get("answer");
        String answer = answerObj != null ? answerObj.toString() : null;
        String explanation = question.getString("explanation");

        if (StringUtils.isNotEmpty(answer) || StringUtils.isNotEmpty(explanation)) {
            if (chineseFont == null) {
                Paragraph answerTitle = new Paragraph("答案解析：")
                        .setFontSize(QUESTION_ANSWER_FONT_SIZE)
                        .setMarginTop(4f)
                        .setMarginBottom(4f)
                        .setBold();
                document.add(answerTitle);
            } else {
                Paragraph answerTitle = new Paragraph("答案解析：")
                        .setFont(chineseFont)
                        .setFontSize(QUESTION_ANSWER_FONT_SIZE)
                        .setMarginTop(4f)
                        .setMarginBottom(4f)
                        .setBold();
                document.add(answerTitle);
            }

            if (StringUtils.isNotEmpty(answer)) {
                if (chineseFont == null) {
                    Paragraph answerPara = new Paragraph("答案：" + answer)
                            .setFontSize(QUESTION_ANSWER_FONT_SIZE)
                            .setMarginBottom(4f);
                    document.add(answerPara);
                } else {
                    Paragraph answerPara = new Paragraph("答案：" + answer)
                            .setFont(chineseFont)
                            .setFontSize(QUESTION_ANSWER_FONT_SIZE)
                            .setMarginBottom(4f);
                    document.add(answerPara);
                }
            }

            if (StringUtils.isNotEmpty(explanation)) {
                if (chineseFont == null) {
                    Paragraph explanationPara = new Paragraph("解析：" + explanation)
                            .setFontSize(QUESTION_ANSWER_FONT_SIZE)
                            .setMarginBottom(10f);
                    document.add(explanationPara);
                } else {
                    Paragraph explanationPara = new Paragraph("解析：" + explanation)
                            .setFont(chineseFont)
                            .setFontSize(QUESTION_ANSWER_FONT_SIZE)
                            .setMarginBottom(10f);
                    document.add(explanationPara);
                }
            }
        }

        // 题目间间距
        document.add(new Paragraph().setMarginBottom(8f));
    }

    /**
     * 获取选项标签
     *
     * @param index 选项索引
     * @return 选项标签（A、B、C、D等）
     */
    private static String getOptionLabel(int index) {
        return String.valueOf((char) ('A' + index));
    }
}