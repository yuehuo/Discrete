layui.use(['laydate', 'laypage', 'layer', 'table', 'form', 'upload'], function() {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table
        , form = layui.form
        , upload = layui.upload//文件上传
        , $ = layui.$
    //向世界问个好
    layer.msg('hello world');
    //渲染表格
    table.render({
        elem: '#videouser'
        ,height: 500
        ,url: '/discrete/admin/video/uservideolist' //后端的视频列表接口
        ,title: '视频列表'
        ,page: true //开启分页,默认传参page=1&limit=10
        ,toolbar: '#headBar' //开启头工具栏，default是默认，也可以自己定义，引入对应的id
        ,totalRow: true //开启合计行
        ,cols: [[ //表头

            {type: 'checkbox', fixed: 'left'}
            // field的值必须跟返回类的属性一致，可以将返回的数据自动赋值给表格
            ,{field: 'id', title: 'ID',width: "6%",sort:true}
            ,{field: 'name', title: '视频名字'}
            ,{field: 'path', title: '视频地址',templet:function (d) {
                    return "<a href="+d.path+">"+"预览"+"</a>";
                }
            }
            ,{field: 'uId', title: '上传者id',sort:true}
            ,{field: 'uName', title: '上传者名字'}
            ,{field: 'uProfile', title: '上传者头像',templet:function (d) {
                    //d:是每行对应的用户对象
                    return "<img src="+d.uProfile+">";
                }
             }
            ,{field: 'creationdate', title: '上传日期',sort:true,templet : "<div>{{layui.util.toDateString(d.creationdate, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            ,{fixed: 'right', align:'center',width: "11%", toolbar: '#barDemo'}//引用行工具栏标签
        ]]
    });
    //文件上传
    var uploadInst = upload.render({
        elem: '#chooseVideo'//上传文件按钮的id
        , url: '/discrete/admin/video/upload' //上传音频接口
        ,data:{  //上传时提交的参数
            parentName:"video",//存储文件的父文件夹
        }
        ,field:"uploadFile"//指定文件域的字段名，跟后端的MultipartFile保持一致
        ,accept:"video"//接受视频文件上传
        ,auto:true
        // , before: function (obj) {
        //     //上传之后回显
        //     obj.preview(function (index, file, result) {
        //         $('#demo2').attr({'src': result, 'width': '120px', 'height': '120px'}); //图片链接（base64）
        //     });
        // }
        //done:上传之后执行的函数
        , done: function (res) {
            //将文件的相对路径赋值给页面中的隐藏输入框input
            $("#path").val(res.data)
            layer.msg(res.data)
        }
    });
    //监听头工具栏事件
    //监听表格怎么指定：使用表格的lay-filter的取值
    table.on('toolbar(videouser)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取所有选中行的数据
        //obj.event:是每个按钮lay-event的取值
        switch(obj.event){
            case 'add':
                //弹出add_video页面
                layer.open({
                    type:2,//1（页面层）2（iframe层）3（加载层）
                    title:"上传视频",
                    content:"/discrete/add_video",
                    area: ['80%', '70%'],//指定宽高
                    shade:0.8,//调节遮罩层的透明度
                    shadeClose:true
                })
                break;
            case 'deleteBatch':
                //遍历data,获取所有选中行数据的alId,添加到数组中
                var ids = []
                for(var i=0;i<data.length;i++){
                    ids.push(data[i].id)
                }
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    $.ajax({
                        url:"/discrete/admin/video/deleteBatch",
                        type:"post",
                        data:{
                            ids:ids
                        },
                        //前端向后端使用ajax传递数据，后端直接接收数组的话需要设置traditional:true
                        traditional:true,
                        success:function (res) {
                            //重新加载当前表格
                            //window.location.reload()
                            //刷新表格
                            table.reload('videouser');
                        }
                    })
                }
                break;
        };
    });
    //submit_artist:是表单提交按钮的lay-filter属性取值
    form.on('submit(video_add)', function(data){
        // console.log(data.elem) //点击的button对象
        // console.log(data.form) //提交的form对象
        console.log(data)
        console.log(data.field) //全部表单字段，名值对形式：{name: value}
        $.ajax({
            url:"/discrete/admin/video/add",//后端添加歌手的接口
            type:"post",
            data:data.field,//data:向后端传递的参数
            success:function(res){  //回调函数
                //父窗口重新加载
                window.parent.location.reload();
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    // 监听行工具栏事件：删除视频与更新视频
    table.on('tool(videouser)', function(obj) {
        // 获取当前行数据和lay-event的值
        var data = obj.data;
        var event = obj.event;

        // 删除视频事件
        if (event === 'deleteVideo') {
            layer.confirm('确定删除该文件吗？', function(index) {
                // ajax方式删除视频
                $.ajax({
                    url:"/discrete/admin/video/deleteVideo",
                    type:"post",
                    data:{
                        id:data.id
                    },
                    success: function(data) {
                        if (data.code == 0) {
                            layer.msg('删除成功');
                            table.reload('videouser');
                        } else {
                            layer.msg('删除失败');
                        }
                    },
                    error: function() {
                        console.log("ajax error");
                    }
                });
                layer.close(index);
            });
        }
        // 更新视频事件
        if (event === 'updateVideo') {
            // 显示更新视频表单的弹出层
            console.log(data)
            var id = data.id;
            console.log("/discrete/update_video?id="+id)
            layer.open({
                type: 2,
                title:"编辑视频",
                content:"/discrete/update_video?id="+id,
                area: ['80%', '70%'],//指定宽高
                shade:0.8,//调节遮罩层的透明度
                shadeClose:true
            });
        }
    });

    // 更新视频表单提交
    form.on('submit(video_update)', function(data) {
        // ajax方式更新订单
        console.log(data.field) //全部表单字段，名值对形式：{name: value}
        $.ajax({
            url:"/discrete/admin/video/updateVideo",
            type:"post",
            data: data.field,//data:向后端传递的参数
            success: function(res) {
                //父窗口重新加载
                window.parent.location.reload();
            },
            error: function() {
                console.log("ajax error");
            }
        })
        // 阻止表单跳转
        return false;
    });
    //以下是搜索框进行监测
    var active = {
        reload: function(){
            var name = $('#name');	//得到搜索框里已输入的数据
            //执行重载
            table.reload('videouser', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    name:  name.val(),	//在表格中进行搜索
                }
            });
        }
    };

    $('#btn .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
})