package cn.sjzcjx.sjzcjx_boot.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/16 10:34
 **/


public class MyBatisPlusGenerator {

    public static final String TABLE_NAME = "file";
    public static final String AUTHOR = "hasd";
    public static final String DIR = "D:/Code/sjzcjx_boot/";
    public static final String PARENT_CLASS = "cn.sjzcjx.sjzcjx_boot";

    @Value("${spring.datasource.url}")
    public static final String DB_URL = "";
    @Value("${spring.datasource.username}")
    public static final String DB_USERNAME = "";
    @Value("${spring.datasource.password}")
    public static final String DB_PASSWORD = "";
    //Mapper 路径
    public static final String OUT_PUT_FILE = DIR + "src/main/resources/mapper";
    // 父级路径
    public static final String PARENT_PATH = DIR + "src/main/java";


    public static void main(String[] args) {
        FastAutoGenerator.create(
                        DB_URL,
                        DB_USERNAME,
                        DB_PASSWORD)
                .globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            //.enableSwagger () // 开启 swagger 模式，这个没啥用
                            .outputDir(PARENT_PATH)
                            .disableOpenDir() // 不打开目录
                            .enableSwagger() //swagger模式
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder -> {
                    builder.parent(PARENT_CLASS) // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, OUT_PUT_FILE));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAME)// 设置表名
                            .entityBuilder()    //entity 前置，才能用 lombok
                            .enableLombok() //lombok 注解
                            .mapperBuilder()//mapper 注解
                            .enableMapperAnnotation()// 使用 lombok
                            .controllerBuilder() //RestController 前置
                            .enableRestStyle();// 使用 RestController
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
