layui.use(['upload','laydate', 'laypage', 'layer', 'table','form'], function() {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , $ = layui.$
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
    //向世界问个好
    layer.msg('hello world');
    //渲染表格
    table.render({
        elem: '#userlist'
        ,height: 500
        ,url: '/discrete/admin/user/list' //后端的用户列表接口
        ,title: '用户列表'
        ,page: true //开启分页,默认传参page=1&limit=10
        ,toolbar: '#headBar' //开启头工具栏，default是默认，也可以自己定义，引入对应的id
        ,totalRow: true //开启合计行
        ,cols: [[ //表头

            {type: 'checkbox', fixed: 'left'}
            // field的值必须跟返回类的属性一致，可以将返回的数据自动赋值给表格
            ,{field: 'id', title: 'ID',width: "6%",sort:true}
            ,{field: 'usercode', title: '账号'}
            ,{field: 'userpassword', title: '密码',width: "6%"}
            ,{field: 'username', title: '名字'}
            ,{field: 'age', title: '年龄',width: "7%",sort:true}
            ,{field: 'sex', title: '性别',width: "6%"}
            ,{field: 'birthday', title: '出生日期',sort:true,templet : "<div>{{layui.util.toDateString(d.birthday, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            ,{field: 'phone', title: '手机'}
            ,{field: 'address', title: '地址'}
            ,{field: 'roleName', title: '职位'}
            ,{field: 'profile', title: '头像',width: "6%",templet:function (d) {
                    //d:是每行对应的用户对象
                    return "<img src="+d.profile+">";
                }
            }
            ,{fixed: 'right', align:'center',width: "11%", toolbar: '#barDemo'}//引用行工具栏标签
        ]]
    });

    //监听头工具栏事件
    //监听表格怎么指定：使用表格的lay-filter的取值
    table.on('toolbar(userlist)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取所有选中行的数据
        //obj.event:是每个按钮lay-event的取值
        switch(obj.event){
            case 'add':
                //弹出add_user页面
                layer.open({
                    type:2,//1（页面层）2（iframe层）3（加载层）
                    title:"添加用户",
                    content:"/discrete/add_user",
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
                        url:"/discrete/admin/user/deleteBatch",
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
                            table.reload('userlist');
                        }
                    })
                }
                break;
        };
    });
    // 监听行工具栏事件：删除用户与更新用户
    table.on('tool(userlist)', function(obj) {
        // 获取当前行数据和lay-event的值
        var data = obj.data;
        var event = obj.event;

        // 删除用户事件
        if (event === 'deleteUser') {
            layer.confirm('确定删除该用户吗？', function(index) {
                // ajax方式删除用户
                $.ajax({
                    url:"/discrete/admin/user/deleteUser",
                    type:"post",
                    data:{
                        id:data.id
                    },
                    success: function(data) {
                        if (data.code == 0) {
                            layer.msg('删除成功');
                            table.reload('userlist');
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
        // 更新用户事件
        if (event === 'updateUser') {
            // 显示更新用户表单的弹出层
            console.log(data)
            var id = data.id;
            console.log("/discrete/update_user?id="+id)
            layer.open({
                type: 2,
                title:"编辑用户",
                content:"/discrete/update_user?id="+id,
                area: ['80%', '70%'],//指定宽高
                shade:0.8,//调节遮罩层的透明度
                shadeClose:true
            });
        }
    });
    // 更新用户表单提交
    form.on('submit(user_update)', function(data) {
        // ajax方式更新用户
        console.log(data.field) //全部表单字段，名值对形式：{name: value}
        $.ajax({
            url:"/discrete/admin/user/updateUser",
            type:"post",
            data:data.field,//data:向后端传递的参数
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
            var usercode = $('#usercode');	//得到搜索框里已输入的数据
            var gender = $('#gender');
            //执行重载
            table.reload('userlist', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    usercode:  usercode.val(),	//在表格中进行搜索
                    gender:  gender.val()
                }
            });
        }
    };

    $('#btn .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
})