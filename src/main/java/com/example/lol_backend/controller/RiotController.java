package com.example.lol_backend.controller;

import com.example.lol_backend.service.RiotService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://recommendchamp.vercel.app")
@RestController
@RequestMapping("/api/riot")
public class RiotController {

    private final RiotService riotService;

    @Autowired
    public RiotController(RiotService riotService) {
        this.riotService = riotService;
    }

    @GetMapping("/recommend")
    public String getRecommendation(@RequestParam String riotId) {
        try {
            JSONObject result = riotService.getRecommendations(riotId);
            return result.toString();
        } catch (Exception e) {
            return errorJson(e.getMessage());
        }
    }

    @GetMapping("/check")
    public String checkUser(@RequestParam String riotId) {
        try {
            boolean exists = riotService.checkUserExists(riotId);
            return new JSONObject().put("exists", exists).toString();
        } catch (Exception e) {
            return errorJson(e.getMessage());
        }
    }

    private String errorJson(String message) {
        return new JSONObject().put("error", message.replace("\"", "'")).toString();
    }
}
