layui.use(['upload', 'element', 'layer','form'], function() {
    var $ = layui.jquery
        , upload = layui.upload
        , element = layui.element
        , layer = layui.layer
        , form = layui.form
        , $ = layui.$; //导入jquery

    //常规使用 - 普通图片上传
    const uploadInst = upload.render({
        elem: '#chooseImg'//上传文件按钮的id
        , url: '/discrete/admin/user/upload' //上传用户头像接口
        , data: {  //上传时提交的参数
            parentName: "img",//存储文件的父文件夹
        }
        , field: "uploadFile"//指定文件域的字段名，跟后端的MultipartFile保持一致
        , accept: "images"//只接受图片类型上传
        , auto: true//选择图片之后直接上传
        , before: function (obj) {
            //上传之后回显
            obj.preview(function (index, file, result) {
                $('#demo1').attr({'src': result, 'width': '120px', 'height': '120px'}); //图片链接（base64）
            });
        }
        //done:上传之后执行的函数
        , done: function (res) {
            //将图片的相对路径赋值给页面中的隐藏输入框input
            $("#profile").val(res.data)
        }
    });

    //submit_artist:是表单提交按钮的lay-filter属性取值
    form.on('submit(user_add)', function(data){
        // console.log(data.elem) //点击的button对象
        // console.log(data.form) //提交的form对象
        console.log(data)
        console.log(data.field) //全部表单字段，名值对形式：{name: value}
        $.ajax({
            url:"/discrete/admin/user/add",//后端添加歌手的接口
            type:"post",
            data:data.field,//data:向后端传递的参数
            success:function(res){  //回调函数
                //父窗口重新加载
                window.parent.location.reload();
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    // //submit_artist:是表单提交按钮的lay-filter属性取值
    // form.on('submit(user_add2)', function(data){
    //     // console.log(data.elem) //点击的button对象
    //     // console.log(data.form) //提交的form对象
    //     console.log(data)
    //     console.log(data.field) //全部表单字段，名值对形式：{name: value}
    //     $.ajax({
    //         url:"/discrete/add2",//后端添加歌手的接口
    //         type:"post",
    //         data:data.field,//data:向后端传递的参数
    //         success:function(res){  //回调函数
    //             //父窗口重新加载
    //             window.parent.location.reload();
    //         }
    //     })
    //     return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    // });
})