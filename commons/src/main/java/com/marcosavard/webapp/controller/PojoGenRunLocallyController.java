package com.marcosavard.webapp.controller;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.webapp.model.PojoModel;
import com.marcosavard.webapp.service.PojoGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

//@Controller
public class PojoGenRunLocallyController {
    @Autowired
    private PojoGenService pojoGenService;

    @GetMapping("/pojogen/run/locally")
    public String fileinfo(HttpServletRequest request, Model model) {
        return "pojogen-run-locally";
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
