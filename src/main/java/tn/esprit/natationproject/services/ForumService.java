package tn.esprit.natationproject.services;


import tn.esprit.natationproject.Entite.*;
import tn.esprit.natationproject.Entite.Thread;
import tn.esprit.natationproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;


    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getComments();
    }
    public List<Post> getPostsByThreadId(Long threadId) {
        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Thread not found"));
        return thread.getPosts();
    }


    // Thread methods
    public Thread createThread(String title, Utilisateurs author) {
        Thread thread = new Thread();
        thread.setTitle(title);
        thread.setAuthor(author);
        return threadRepository.save(thread);
    }


    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    public Thread getThreadById(Long id) {
        return threadRepository.findById(id).orElseThrow(() -> new RuntimeException("Thread not found"));
    }

    public Thread updateThread(Thread thread) {
        return threadRepository.save(thread);
    }

    public void deleteThread(Long id) {
        threadRepository.deleteById(id);
    }

    // Post methods
    public Post createPost(Thread thread, String content, Utilisateurs author) {
        Post post = new Post();
        post.setThreads(thread);
        post.setContent(content);
        post.setAuthor(author);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // Comment methods
    public Comment createComment(Comment comment) {

        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
