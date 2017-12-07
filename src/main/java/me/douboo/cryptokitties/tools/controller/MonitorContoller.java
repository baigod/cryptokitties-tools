package me.douboo.cryptokitties.tools.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class MonitorContoller {

	@RequestMapping(method = RequestMethod.GET)
	public String summary() {
		return "index";
	}

	@RequestMapping(path = "/path/**", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> path(HttpServletRequest request) {
		String path = request.getServletPath();
		path = path.replace("/path", "");
		Map<String, Object> result = new HashMap<String, Object>();
		return result;
	}

	@RequestMapping(path = "/data/**", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request) {
		String path = request.getServletPath();
		path = path.replace("/data", "");
		Map<String, Object> result = new HashMap<String, Object>();
		return result;
	}
}
