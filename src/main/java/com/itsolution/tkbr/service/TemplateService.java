/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Access;
import com.itsolution.tkbr.domain.AccessGroup;
import com.itsolution.tkbr.domain.Authority;
import com.itsolution.tkbr.domain.PersistentAuditEvent;
import com.itsolution.tkbr.domain.PersistentToken;
import com.itsolution.tkbr.domain.User;
import com.itsolution.tkbr.service.util.FieldUtils;
import com.itsolution.tkbr.service.util.InputType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.annotations.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 *
 * @author tchipi
 */
@Service
public class TemplateService {
    
    private final Logger log = LoggerFactory.getLogger(TemplateService.class);
    
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private EntityManager em;
    @Autowired
    private ResourceLoader resourceLoader;
    
    public void processTemplateEngine(String basePackage) throws Exception {
        log.debug("Start entity templates Rendering");
        cleanUpDirectories();
        String index = "";
        Metamodel metamodel = em.getMetamodel();
        for (EntityType et : metamodel.getEntities()) {
            if (et.getJavaType().equals(User.class)
                   || et.getJavaType().equals(Authority.class)
                   || et.getJavaType().equals(Access.class)
                   || et.getJavaType().equals(AccessGroup.class)
                   || et.getJavaType().equals(Authority.class)
                    || et.getJavaType().equals(PersistentToken.class)
                    || et.getJavaType().equals(PersistentAuditEvent.class)) {
                continue;
            }
            
            Context context = new Context();
            context.setVariable("entity", et.getJavaType().getSimpleName());
            context.setVariable("entity_var", et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1)));
            context.setVariable("entity_url", entityUrl(et.getJavaType().getSimpleName()));
            context.setVariable("entityid", et.getIdType().getJavaType().getSimpleName());
            context.setVariable("entitypackage", et.getJavaType().getCanonicalName());
            
            context.setVariable("repositorypackage", basePackage.concat(".repository"));
            context.setVariable("searchrepositorypackage", basePackage.concat(".repository.search"));
            context.setVariable("controllerpackage", basePackage.concat(".web.rest"));
            
            creatingEntityRepositories(context);
            if (et.getJavaType().isAnnotationPresent(Cache.class)) {
                creatingEntitySearchRepositories(context);
            }
            creatingEntityController(context,et.getJavaType().isAnnotationPresent(Cache.class));
            creatingEntityClient(context, et);
            
            index += "  <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + "-dialog.controller.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + "-detail.controller.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + "-delete-dialog.controller.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".state.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".service.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".search.service.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".controller.js\"></script>\n";
        }
        creatingFiles(index, "index.html");
        log.debug("End entity templates Rendering");
    }
    
    public void creatingEntityClient(Context context, EntityType et) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " Client Files");
        creatingEntityClientPart(context, et, "-delete-dialog.controller.js");
        creatingEntityClientPart(context, et, "-delete-dialog.html");
        
        context.setVariable("entity_selects_header", entitySelectsHeader(et));
        context.setVariable("entity_selects_header1", entitySelectsHeader1(et));
        context.setVariable("entity_selects", entitySelects(et));
        context.setVariable("entity_dates", entityDates(et));
        creatingEntityClientPart(context, et, "-dialog.controller.js");
        
        context.setVariable("form", entityForm(et));
        creatingEntityClientPart(context, et, "-dialog.html");
        
        context.setVariable("entity_detail", entityDetail(et));
        creatingEntityClientPart(context, et, "-detail.html");
        creatingEntityClientPart(context, et, "-detail.controller.js");
        
        creatingEntityClientPart(context, et, ".controller.js");
        creatingEntityClientPart(context, et, ".search.service.js");
        creatingEntityClientPart(context, et, ".service.js");
        creatingEntityClientPart(context, et, ".state.js");
        
        context.setVariable("tableHeader", entityTableHeader(et));
        context.setVariable("tableBody", entityTableBody(et));
        creatingEntityClientPart(context, et, "s.html");
        
    }
    
    public void creatingEntityClientPart(Context context, EntityType et, String file) throws Exception {
        String content = templateEngine.process("entity" + file, context);
        creatingFiles(content, "views" + File.separator + context.getVariable("entity_url") + File.separator + context.getVariable("entity_url") + file);
        
    }
    
    public void creatingEntityRepositories(Context context) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " repositories");
        String content = templateEngine.process("TemplateRepository.txt", context);
        creatingFiles(content, "repository" + File.separator + (String) context.getVariable("entity") + "Repository.java");
    }
    
    public void creatingEntitySearchRepositories(Context context) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " Searchrepositories");
        String content = templateEngine.process("TemplateSearchRepository.txt", context);
        creatingFiles(content, "searchrepository" + File.separator + (String) context.getVariable("entity") + "SearchRepository.java");
    }
    
    public void creatingEntityController(Context context,boolean search) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " controllers ");
        String content = templateEngine.process("TemplateController"+(search?"Search":"")+".txt", context);
        creatingFiles(content, "controller" + File.separator + (String) context.getVariable("entity") + "Resource.java");
    }
    
    public void creatingFiles(String content, String filename) throws Exception {
        Resource dir = resourceLoader.getResource("classpath:templates");
        // create file-in-subdirectory path
        Path file = Paths.get(dir.getFilename() + File.separator + filename);
        
        if (!Files.exists(file.getParent())) {
            try {
                Files.createDirectory(file.getParent());
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        //    deleteAllFilesDirOrCreateDir(Paths.get(dir.getFilename()), false);
        Files.write(file, content.getBytes());
    }
    
    public void cleanUpDirectories() throws IOException {
        Resource dir = resourceLoader.getResource("classpath:templates");
        deleteAllFilesDirOrCreateDir(Paths.get(dir.getFilename()), false);
        
    }
    
    public static String entityUrl(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        result += Character.toLowerCase(firstChar);
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result += "-" + Character.toLowerCase(currentChar);
            } else {
                result += currentChar;
            }
        }
        return result;
    }
    
    public static String toLowerCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToLowerCase = Character.toLowerCase(firstChar);
        result += firstCharToLowerCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result += currentCharToLowerCase;
        }
        return result;
    }
    
    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result += firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result += currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result += currentCharToLowerCase;
            }
        }
        return result;
    }
    
    public static void deleteAllFilesDirOrCreateDir(Path dir, boolean deletedir) throws IOException {
        if (Files.exists(dir)) {
            deleteAllFilesDir(dir, deletedir);
        } else {
            Files.createDirectories(dir);
        };
    }
    
    public static void deleteAllFilesDir(Path dir, boolean deletedir) {
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    System.out.println("Deleting file: " + file);
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                        IOException exc) throws IOException {
                    if (deletedir) {
                        System.out.println("Deleting dir: " + dir);
                        if (exc == null) {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        } else {
                            throw exc;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
                
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String entityDates(EntityType et) throws Exception {
        String result = "";
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> f.getType().equals(LocalDate.class)
        );
        for (Field f : inputs) {
            result += " vm.datePickerOpenStatus." + f.getName() + " = false;\n";
        }
        return result;
    }
    
    private String entitySelectsHeader(EntityType et) throws Exception {
        String result = "";
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> FieldUtils.getInputType(f).equals(InputType.SELECT)
        );
        for (Field f : inputs) {
            result += ",'" + f.getType().getSimpleName() + "'";
        }
        return result;
    }
    
    private String entitySelectsHeader1(EntityType et) throws Exception {
        String result = "";
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> FieldUtils.getInputType(f).equals(InputType.SELECT)
        );
        for (Field f : inputs) {
            result += "," + f.getType().getSimpleName();
        }
        return result;
    }
    
    private String entitySelects(EntityType et) throws Exception {
        String result = "";
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> FieldUtils.getInputType(f).equals(InputType.SELECT)
        );
        for (Field f : inputs) {
            result += "vm." + f.getType().getSimpleName().toLowerCase().substring(0, 1).concat(f.getType().getSimpleName().substring(1)) + "s = " + f.getType().getSimpleName() + ".query();\n";
        }
        return result;
    }
    
    public String entityForm(EntityType et) throws Exception {
        String entity = et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1));
        String result = "";
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> !f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(CreatedBy.class) && !f.isAnnotationPresent(CreatedDate.class)
                && !f.isAnnotationPresent(LastModifiedBy.class) && !f.isAnnotationPresent(LastModifiedDate.class)
                && !f.getName().equalsIgnoreCase("serialVersionUID")
                && !f.getName().equalsIgnoreCase("$jacocoData")
                && !f.isAnnotationPresent(OneToMany.class)
        );
        for (Field f : inputs) {
            result += " <div class=\"form-group\">\n";
            result += " <label class=\"control-label\"  for=\"field_" + f.getName() + "\">" + FieldUtils.coollabel(f.getName()) + ""
                    + (FieldUtils.isRequiredField(f) ? " <span style=\"color:red\" >*</span>" : "")
                    + "</label>\n";
            switch (FieldUtils.getInputType(f)) {
                case TEXT:
                    result += "  <input  class=\"form-control\"   type=\"text\"  autocomplete=\"off\"   id=\"field_" + f.getName() + "\" ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + (FieldUtils.getMinLenth(f) != null ? "   ng-minlength=\"" + FieldUtils.getMinLenth(f) + "\"  " : "")
                            + (FieldUtils.getMaxLength(f) != null ? "   ng-maxlength=\"" + FieldUtils.getMaxLength(f) + "\"  " : "")
                            + "                    />\n";
                    break;
                
                case EMAIL:
                    result += "  <input  class=\"form-control\"   type=\"email\"  autocomplete=\"off\"  id=\"field_" + f.getName() + "\"  ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + "                    />\n";
                    break;
                case TEL:
                    break;
                case NUMBER:
                    result += "  <input  class=\"form-control\"   type=\"number\"  autocomplete=\"off\"  id=\"field_" + f.getName() + "\" ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + (FieldUtils.getMin(f) != null ? "   ng-min=\"" + FieldUtils.getMin(f) + "\"  " : "")
                            + (FieldUtils.getMax(f) != null ? "   ng-max=\"" + FieldUtils.getMax(f) + "\"  " : "")
                            + "                    />\n";
                    break;
                case TEXTAREA:
                    result += "   <textarea class=\"form-control\" rows=\"3\"  cols=\"30\"   id=\"field_" + f.getName() + "\" ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + "                    > </textarea>\n";
                    break;
                case CHECKBOX:
                    result += " <div class=\"form-control\">\n"
                            + "            <label class=\"i-checks\">\n"
                            + "                <input type=\"checkbox\" checked  ng-model=\"vm." + entity + "." + f.getName() + "\"   id=\"field_" + f.getName() + "\"  name=\"" + f.getName() + "\" /><i></i> \n"
                            + "            </label>\n"
                            + "        </div>\n";
                    break;
                case FILE:
                    break;
                case IMAGE:
                    break;
                case SELECT:
                    String manytoone = f.getType().getSimpleName().toLowerCase().substring(0, 1).concat(f.getType().getSimpleName().substring(1));
                    result += "  <select class=\"form-control\" name=\"" + f.getName() + "\"  ng-model=\"vm." + entity + "." + f.getName() + "\"  id=\"field_" + f.getName() + "\" ng-options=\"" + manytoone + " as " + manytoone + ".id for " + manytoone + " in vm." + manytoone + "s track by " + manytoone + ".id\" " + (FieldUtils.isRequiredField(f) ? "required" : "") + ">\n"
                            + "                <option value=\"\"></option>\n"
                            + "            </select>\n";
                    break;
                case SELECTENUM:
                    result += " <select class=\"form-control\" name=\"" + f.getName() + "\"  ng-model=\"vm." + entity + "." + f.getName() + "\"  id=\"field_" + f.getName() + "\" " + (FieldUtils.isRequiredField(f) ? "required" : "") + ">\n"
                            + FieldUtils.getSelectsEnumerated(f)
                            + "            </select>\n";
                    break;
                case DATE:
                    result += "  <div class=\"input-group\">\n"
                            + "                    <input  id=\"field_" + f.getName() + "\" type=\"text\" class=\"form-control\" name=\"" + f.getName() + "\" uib-datepicker-popup=\"{{dateformat}}\"  ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + " is-open=\"vm.datePickerOpenStatus." + f.getName() + "\"\n"
                            + "                    />\n"
                            + "                    <span class=\"input-group-btn\">\n"
                            + "                        <button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.openCalendar('" + f.getName() + "')\"><i class=\"glyphicon glyphicon-calendar\"></i></button>\n"
                            + "                    </span>\n"
                            + "                </div>\n";
                    break;
                default:
                    break;
            }
            result += " </div>\n";
        }
        return result;
    }
    
    private String entityTableBody(EntityType et) throws Exception {
        String result = "";
        List<String> ths = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            ths.add(" <td>{{" + et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1)) + "." + (field.getName()) + (field.getType().equals(LocalDate.class) ? " | date:'mediumDate'" : "") + "}}</td>");
        }, (java.lang.reflect.Field f)
                -> !f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(CreatedBy.class) && !f.isAnnotationPresent(CreatedDate.class)
                && !f.isAnnotationPresent(LastModifiedBy.class) && !f.isAnnotationPresent(LastModifiedDate.class)
                && !f.getName().equalsIgnoreCase("serialVersionUID")
                && !f.getName().equalsIgnoreCase("$jacocoData")
                && !f.isAnnotationPresent(OneToMany.class)
                && !f.isAnnotationPresent(Lob.class)
        );
        result = ths.stream().collect(Collectors.joining("\n"));
        return result;
    }
    
    private String entityDetail(EntityType et) throws Exception {
        String result = "";
        List<String> ths = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            
            ths.add("<dt><span >" + FieldUtils.coollabel(field.getName()) + "</span></dt>\n"
                    + "        <dd>\n"
                    + "            <span>{{vm." + et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1)) + "." + field.getName() + "}}</span>\n"
                    + "        </dd> ");
        }, (java.lang.reflect.Field f)
                -> !f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(CreatedBy.class) && !f.isAnnotationPresent(CreatedDate.class)
                && !f.isAnnotationPresent(LastModifiedBy.class) && !f.isAnnotationPresent(LastModifiedDate.class)
                && !f.getName().equalsIgnoreCase("serialVersionUID")
                && !f.getName().equalsIgnoreCase("$jacocoData")
        );
        result = ths.stream().collect(Collectors.joining("\n"));
        return result;
    }
    
    private String entityTableHeader(EntityType et) throws Exception {
        String result = "";
        List<String> ths = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            ths.add(" <th jh-sort-by=\"" + field.getName() + "\"><span >" + FieldUtils.coollabel(field.getName()) + "</span> <span class=\"glyphicon glyphicon-sort\"></span></th>");
        }, (java.lang.reflect.Field f)
                -> !f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(CreatedBy.class) && !f.isAnnotationPresent(CreatedDate.class)
                && !f.isAnnotationPresent(LastModifiedBy.class) && !f.isAnnotationPresent(LastModifiedDate.class)
                && !f.getName().equalsIgnoreCase("serialVersionUID")
                && !f.getName().equalsIgnoreCase("$jacocoData")
                && !f.isAnnotationPresent(OneToMany.class)
                && !f.isAnnotationPresent(Lob.class)
        );
        result = ths.stream().collect(Collectors.joining("\n"));
        return result;
    }
    
}
