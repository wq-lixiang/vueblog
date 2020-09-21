import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/admin/login.vue'
import Admin from '../components/admin/admin.vue'
import Blogs from '../components/admin/blogs.vue'
import Tags from  '../components/admin/tags.vue'
import Types from '../components/admin/types.vue'
import Blog_Input from '../components/admin/blog-input.vue'
import Blog from '../components/index/blog.vue'
import ShowBlogs from '../components/index/ShowBlogs.vue'
import TypesBlogs from '../components/index/TypesBlogs.vue'
import TagsBlogs from '../components/index/TagsBlogs.vue'
import ShowBlog from '../components/index/ShowBlog.vue'
import Regit from '../components/admin/register.vue'

Vue.use(Router);

export default new Router({
  routes: [
    {path:'/',component:Blog ,children:[
      {path:'/', redirect:'/index/showBlogs'},
      {path:'/index/showBlogs' ,component:ShowBlogs},
      {path:'/index/typesBlogs',component:TypesBlogs},
      {path:'/index/typesBlogs/:id',component:TypesBlogs},
      {path:'/index/tagsBlogs',component:TagsBlogs},
      {path:'/index/tagsBlogs/:id',component:TagsBlogs},
      {path:'/index/:id' ,component:ShowBlog}
    ]},
    {
      path:'/login',component : Login,
    },
    {
      path:'/register',component : Regit,
    },
    {
      path:'/admin',component:Admin ,children:[
      {path:'/admin',redirect:'/admin/blogs'},
      {path:'/admin/tags' ,component:Tags},
      {path:'/admin/blogs' ,component: Blogs},
      {path:'/admin/types' ,component:Types},
      {path:'/admin/blog_input',component:Blog_Input},
      {path:'/admin/blog_input/:id',component:Blog_Input}
    ]
    },

  ]
})
