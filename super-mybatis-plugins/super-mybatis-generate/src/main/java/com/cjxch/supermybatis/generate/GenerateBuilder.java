package com.cjxch.supermybatis.generate;

import com.alibaba.druid.util.StringUtils;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.annotation.Table;
import com.cjxch.supermybatis.core.tools.CamelCaseUtils;
import com.cjxch.supermybatis.core.tools.ReflectionUtil;
import com.cjxch.supermybatis.generate.util.ClassUtil;
import com.cjxch.supermybatis.generate.util.DBUtil;
import com.cjxch.supermybatis.generate.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Czy
 * @Description 生成工具
 **/
public class GenerateBuilder {

    private String author = "";
    private String entityPath; //实体类路径
    private String savePath; //数据生成磁盘路径
    private boolean generateDao = true;
    private boolean generateService = true;
    private boolean generateSql = true;
    private boolean interfaceFlag = true;

    private String sqlPath;

    private String[] ignore = new String[]{};
    private DbType dbType;

    public static GenerateBuilder builder(){
        return new GenerateBuilder();
    }

    public GenerateBuilder generateDao(boolean generateDao){
        this.generateDao = generateDao;
        return this;
    }
    public GenerateBuilder sqlPath(String sqlPath){
        this.sqlPath = sqlPath;
        return this;
    }

    public GenerateBuilder savePath(String savePath){
        this.savePath = savePath;
        return this;
    }

    public GenerateBuilder ignore(String[] ignore){
        this.ignore = ignore;
        return this;
    }

    public GenerateBuilder interfaceFlag(boolean interfaceFlag){
        this.interfaceFlag = interfaceFlag;
        return this;
    }

    public GenerateBuilder author(String author){
        this.author = author;
        return this;
    }
    public GenerateBuilder entityPath(String entityPath){
        this.entityPath = entityPath;
        return this;
    }
    public GenerateBuilder generateService(boolean generateService){
        this.generateService = generateService;
        return this;
    }
    public GenerateBuilder generateSql(boolean generateSql){
        this.generateSql = generateSql;
        return this;
    }
    public GenerateBuilder dbType(DbType dbType){
        this.dbType = dbType;
        return this;
    }

    public void generate(){
        if(StringUtils.isEmpty(entityPath)){
            throw new RuntimeException("请配置实体类包路径[entityPath]");
        }
        if(StringUtils.isEmpty(savePath)){
            throw new RuntimeException("请配置文件生成磁盘路径[savePath]，一般为实体类所在目录的上一级");
        }
        System.out.println(">>>>>>>>>>>>>>>>>>  Super-Mybatis 自动生成工具类  <<<<<<<<<<<<<<<<<<<<");
        System.out.println("实体类路径地址为：" + entityPath);
        System.out.println("\t实体类信息为：");
        ClassUtil.getClassSet(entityPath).forEach(i -> {
            System.out.println("\t\t" + i.getName());
        });
        if(generateDao) {
            System.out.println("开始生成 Dao ......");
            doGenerateDao();
            System.out.println("Dao 生成完毕......");
        }
        if(generateService) {
            System.out.println("开始生成 Service ......");
            doGenerateService();
            System.out.println("Service 生成完毕......");
        }
        if(generateSql) {
            System.out.println("开始生成 建表Sql......");
            doGenerateSql();
            System.out.println("建表Sql 生成完毕......");
        }
    }

    private void doGenerateDao(){
        System.out.println("开始生成Dao接口类 \r\nDao 接口包路径为：" + entityPath.substring(0, entityPath.lastIndexOf("."))+".dao 磁盘路径为：\" + savePath");
        String pckage = entityPath.substring(0, entityPath.lastIndexOf("."))+".dao";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        for(Class i : ClassUtil.getClassSet(entityPath)) {
            boolean c = false;
            for(String s : ignore){
                if(i.getSimpleName().contains(s)) c = true;
            }
            if(c) continue;
            String entity = i.getName();
            String entityName = i.getSimpleName();
            String iflag = interfaceFlag ? "I" : "";
            String path = savePath + File.separator + "dao" + File.separator + iflag + entityName + "Dao.java";
            String daoInfo = FileUtil.readFile("Dao.tpl").replace("#entity#", entity)
                    .replace("#author#", author).replace("#entityName#", entityName)
                    .replace("#I#", iflag).replace("#package#", pckage)
                    .replace("#date#",date);
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(path)) {
                writer.write(daoInfo);
                System.out.println("\t[" + iflag + entityName + "Dao.java]文件生成成功，路径：" + path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        System.out.println("开始生成Dao接口实现类 \r\nDao 接口实现类包路径为：" + entityPath.substring(0, entityPath.lastIndexOf("."))+".dao 磁盘路径为：\" + savePath");

        for(Class i : ClassUtil.getClassSet(entityPath)) {
            boolean c = false;
            for(String s : ignore){
                if(i.getSimpleName().contains(s)) c = true;
            }
            if(c) continue;
            String entity = i.getName();
            String entityName = i.getSimpleName();
            String iflag = interfaceFlag ? "I" : "";
            String path = savePath + File.separator + "dao" + File.separator + "impl" + File.separator + entityName + "DaoImpl.java";
            String daoInfo = FileUtil.readFile("DaoImpl.tpl").replace("#entity#", entity)
                    .replace("#author#", author).replace("#entityName#", entityName)
                    .replace("#I#", iflag).replace("#package#", pckage)
                    .replace("#date#",date);
            File file = new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try(FileWriter writer = new FileWriter(path)) {
                writer.write(daoInfo);
                System.out.println("\t[" + iflag + entityName + "DaoImpl.java]文件生成成功，路径：" + path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void doGenerateService(){
        System.out.println("开始生成Service接口类 \r\nService 接口包路径为：" + entityPath.substring(0, entityPath.lastIndexOf("."))+".service 磁盘路径为：\" + savePath");
        String pckage = entityPath.substring(0, entityPath.lastIndexOf("."))+".service";
        String daoPackage = entityPath.substring(0, entityPath.lastIndexOf("."))+".dao";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        for(Class i : ClassUtil.getClassSet(entityPath)) {
            boolean c = false;
            for(String s : ignore){
                if(i.getSimpleName().contains(s)) c = true;
            }
            if(c) continue;
            String entity = i.getName();
            String entityName = i.getSimpleName();
            String iflag = interfaceFlag ? "I" : "";
            String path = savePath + File.separator + "service" + File.separator + iflag + entityName + "Service.java";
            String serviceInfo = FileUtil.readFile("Service.tpl").replace("#entity#", entity)
                    .replace("#author#", author).replace("#entityName#", entityName)
                    .replace("#I#", iflag).replace("#package#", pckage)
                    .replace("#date#",date);
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(path)) {
                writer.write(serviceInfo);
                System.out.println("\t[" + iflag + entityName + "ServiceImpl.java]文件生成成功，路径：" + path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        System.out.println("开始生成Service接口实现类 \r\nService 接口实现类包路径为：" + entityPath.substring(0, entityPath.lastIndexOf("."))+".service 磁盘路径为：\" + savePath");

        for(Class i : ClassUtil.getClassSet(entityPath)) {
            boolean c = false;
            for(String s : ignore){
                if(i.getSimpleName().contains(s)) c = true;
            }
            if(c) continue;
            String entity = i.getName();
            String entityName = i.getSimpleName();
            String iflag = interfaceFlag ? "I" : "";
            String path = savePath + File.separator + "service" + File.separator + "impl" + File.separator + entityName + "ServiceImpl.java";
            String serviceInfo = FileUtil.readFile("ServiceImpl.tpl").replace("#entity#", entity).replace("#entityName-2#", entityName.substring(0,1).toLowerCase() + entityName.substring(1))
                    .replace("#author#", author).replace("#entityName#", entityName)
                    .replace("#I#", iflag).replace("#package#", pckage)
                    .replace("#date#",date).replace("#dao-package#", daoPackage);
            File file = new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try(FileWriter writer = new FileWriter(path)) {
                writer.write(serviceInfo);
                System.out.println("\t[" + iflag + entityName + "Service.java]文件生成成功，路径：" + path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void doGenerateSql()  {
        System.out.println("开始生成建表SQL文件");
        StringBuilder tableSql = new StringBuilder();
        for(Class i : ClassUtil.getClassSet(entityPath)) {
            try {
                StringBuilder sb = new StringBuilder();
                Table a = i.newInstance().getClass().getAnnotation(Table.class);
                if (a == null){
                    System.out.println("实体类["+i.getSimpleName()+"]没有添加@Table注解，直接跳过");
                    continue;
                }
                String tableName = CamelCaseUtils.processNameWithUnderLine(i.getSimpleName());
                if (!StringUtils.isEmpty(a.value())) tableName = a.value();
                sb.append("create table " + tableName + "\r\n");
                sb.append("(" + "\r\n");
                Field[] declaredField = ReflectionUtil.getDeclaredField(i.newInstance());
                int ii = 0;
                for(Field f : declaredField){
                    String typeName = f.getType().getSimpleName();
                    sb.append("    " + CamelCaseUtils.processNameWithUnderLine(f.getName()) + "    " + DBUtil.propertiesToDb(typeName));
                    PrimaryKey primaryKey = f.getAnnotation(PrimaryKey.class);
                    if(primaryKey != null) sb.append("    not null    primary key");
                    else  sb.append("    null");
                    ii++;
                    if(ii < declaredField.length) sb.append(",\r\n");
                    else sb.append("\r\n");
                }
                sb.append(");" + "\r\n");

                if(StringUtils.isEmpty(sqlPath)){
                    System.out.println("#>>>>>>>>>>>>>>>["+i.getSimpleName()+"]建表语句创建成功：");
                    System.out.println(sb);
                }else{
                    tableSql.append("#"+i.getSimpleName()+"\r\n");
                    tableSql.append(sb+"\r\n");
                }
            }catch(Exception e){
                System.out.println("实体类["+i.getSimpleName()+"]建表语句生成失败：" + e.getMessage());
            }
        }

        if(!StringUtils.isEmpty(sqlPath)){
            try(FileWriter writer = new FileWriter(sqlPath + File.separator + "db.sql")) {
                writer.write(tableSql.toString());
                System.out.println("\t建表语句生成成功，路径：" + sqlPath + File.separator + "db.sql");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
