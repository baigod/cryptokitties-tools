package me.douboo.cryptokitties.tools.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class MonitorContoller {

	@GetMapping({ "", "/" })
	public String index() {
		return "index";
	}

	@PostMapping(path = "/data" )
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request) {
		String path = request.getServletPath();
		path = path.replace("/data", "");
		Map<String, Object> result = new HashMap<String, Object>();
		return result;
	}
}
