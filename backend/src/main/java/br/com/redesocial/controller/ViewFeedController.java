package br.com.redesocial.controller;

import br.com.redesocial.model.ViewFeed;
import br.com.redesocial.repository.ViewFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/viewFeed")
public class ViewFeedController {
    private final ViewFeedRepository viewFeedRepository;

    public ViewFeedController(ViewFeedRepository viewFeedRepository) {
        this.viewFeedRepository = viewFeedRepository;
    }

    @GetMapping("/feed")
    public List<ViewFeed> getFeed() {
        return viewFeedRepository.findAll();
    }
}
