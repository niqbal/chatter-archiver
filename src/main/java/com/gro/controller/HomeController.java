package com.gro.controller;

import com.gro.DataAccess.ChatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    private ChatterService chatterService;

    @RequestMapping(value="/getfeeds", method=RequestMethod.GET)
    public ModelAndView getFeeds(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ModelAndView mv = new ModelAndView("getfeeds");
        mv.addObject("myfeeds", chatterService.getMentions());
        return mv;
    }

    @RequestMapping(value="/getarchived", method=RequestMethod.GET)
    public ModelAndView getArchived(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ModelAndView mv = new ModelAndView("getfeeds");
        mv.addObject("myfeeds", chatterService.getArchived());
        return mv;
    }

}
