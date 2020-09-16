package com.wy;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        //数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/mmall");
        generator.setDataSource(dataSourceConfig);
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        globalConfig.setAuthor("wy");
        globalConfig.setOpen(false);
        globalConfig.setServiceName("%sService");
        generator.setGlobalConfig(globalConfig);
        //包信息
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.wy");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");
        generator.setPackageInfo(packageConfig);
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //不写就默认包含全部表
        //strategyConfig.setInclude("account");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        TableFill tableFill1 = new TableFill("create_time", FieldFill.INSERT);
        TableFill tableFill2 = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        List<TableFill> list = new ArrayList<>();
        list.add(tableFill1);
        list.add(tableFill2);
        strategyConfig.setTableFillList(list);
        generator.setStrategy(strategyConfig);
        generator.execute();
    }
}
