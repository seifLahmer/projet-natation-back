package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.PostDTO;
import org.example.backend.dto.CommentDTO;
import org.example.backend.dto.ThreadDTO;
import org.example.backend.model.Thread;
import org.example.backend.model.*;
import org.example.backend.repository.PostRepository;
import org.example.backend.repository.ThreadRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.ForumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ForumController {
    @Autowired
    private ForumService forumService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/threads")
    public ThreadDTO createThread(@RequestBody Thread thread) {
        // Removed authentication - you might want to handle author differently now
        Utilisateur author = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Default user not found"));
        Thread threads = forumService.createThread(thread.getTitle(), author);
        return modelMapper.map(threads, ThreadDTO.class);
    }

    @GetMapping("/threads")
    public List<ThreadDTO> getThreads() {
        List<Thread> threads = forumService.getAllThreads();
        return threads.stream()
                .map(thread -> modelMapper.map(thread, ThreadDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/threads/{id}")
    public Thread getThread(@PathVariable Long id) {
        return forumService.getThreadById(id);
    }

    @PutMapping("/threads")
    public Thread updateThread(@RequestBody Thread thread) {
        return forumService.updateThread(thread);
    }

    @PutMapping("/threads/update")
    public ResponseEntity<ThreadDTO> updateThreadTitle(@RequestBody Thread thread) {
        Thread thread1 = forumService.getThreadById(thread.getId());
        thread1.setTitle(thread.getTitle());
        Thread updatedThread = forumService.updateThread(thread1);
        return ResponseEntity.ok(modelMapper.map(updatedThread, ThreadDTO.class));
    }

    @DeleteMapping("/threads/{id}")
    public void deleteThread(@PathVariable Long id) {
        forumService.deleteThread(id);
    }


    @PostMapping("/posts/{threadId}")
    public PostDTO createPost(@PathVariable Long threadId, @RequestBody PostDTO postDTO) {
        Thread thread = threadRepository.findById(threadId).orElseThrow(() -> new RuntimeException("Thread not found"));
        // Using a default user since authentication is removed
        Utilisateur author = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Default user not found"));
        Post post = new Post();
        post.setAuthor(author);
        post.setThreads(thread);
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        Post createdPost = forumService.createPost(thread, post.getContent(), author);
        return modelMapper.map(createdPost, PostDTO.class);
    }

    @GetMapping("/threads/{threadId}/posts")
    public List<PostDTO> getPostsByThreadId(@PathVariable Long threadId) {
        List<Post> posts = forumService.getPostsByThreadId(threadId);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/posts/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        Post post = forumService.getPostById(id);
        return modelMapper.map(post, PostDTO.class);
    }

    @PutMapping("/posts")
    public Post updatePost(@RequestBody Post post) {
        return forumService.updatePost(post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        forumService.deletePost(id);
    }

    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        // Using a default user since authentication is removed
        Utilisateur author = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Default user not found"));
        comment.setAuthor(author);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        return forumService.createComment(comment);
    }

    @GetMapping("/comments")
    public List<Comment> getComments() {
        return forumService.getAllComments();
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = forumService.getCommentsByPostId(postId);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/comments/{id}")
    public Comment getComment(@PathVariable Long id) {
        return forumService.getCommentById(id);
    }

    @PutMapping("/comments")
    public Comment updateComment(@RequestBody Comment comment) {
        return forumService.updateComment(comment);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        forumService.deleteComment(id);
    }
}