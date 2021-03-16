package com.example.techie_desi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class BloggerApi {
    public static final String key = "AIzaSyBo0mZgSAIPtN3oWy9QzkFYu14xujLcNlw";
    public static final String url ="https://www.googleapis.com/blogger/v3/blogs/5799392152033158522/posts/";
    public static PostService postService=null;

    public static PostService getService()
    {
        if(postService==null)
        {
            Retrofit retrofit= new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postService=retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService{
        @GET
        Call<PostList> getPostList(@Url String url);

//    @GET("{postId}/?key"+key) //can obtain through post id.
//    Call<Item> getPostById(@Path("postId") String id);
    }
}

