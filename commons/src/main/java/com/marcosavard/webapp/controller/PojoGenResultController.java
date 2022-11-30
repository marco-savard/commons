package com.marcosavard.webapp.controller;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.webapp.model.FileData;
import com.marcosavard.webapp.model.PojoModel;
import com.marcosavard.webapp.service.FileInfoService;
import com.marcosavard.webapp.service.PojoGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@Controller
public class PojoGenResultController {
    @Autowired
    private PojoGenService pojoGenService;

    @GetMapping("/pojogen/result")
    public String fileinfo(HttpServletRequest request, Model model) {
        PojoModel pojoModel = pojoGenService.getPojoModel();
        Map<MetaClass, String> pojos = pojoModel.getPojos();
        String modelCode = pojoModel.getModelAsString();
        long sourceLoc = StringUtil.countCharacters(modelCode, '\n');
        long generatedLOC = findGeneratedLOC(pojoModel);

        model.addAttribute("sourceFile", pojoModel.getSourceFile());
        model.addAttribute("sourceLoc", Long.toString(sourceLoc) + " LOC");
        model.addAttribute("generatedFile", pojoModel.getGeneratedFile());
        model.addAttribute("nbPojos", pojos.keySet().size() + " POJOs");
        model.addAttribute("generatedLOC", Long.toString(generatedLOC) + " LOC");
        return "pojogen-result";
    }

    private long findGeneratedLOC(PojoModel pojoModel) {
        long totalLoc = 0L;
        Set<MetaClass> keys = pojoModel.getPojos().keySet();

        for (MetaClass mc : keys) {
            String code = pojoModel.getPojos().get(mc);
            long loc = StringUtil.countCharacters(code, '\n');
            totalLoc += loc;
        }

        return totalLoc;
    }
}
