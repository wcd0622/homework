<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>简历列表</title>
    <style>
        #bgDiv {
            position: absolute;
            left: 0;
            top: 0;
            background-color: black;
            opacity: 0.2; /*设置不透明度*/
            display: none;
        }
        #fgDiv {
            position: absolute;
            width: 250px;
            height: 100px;
            border: 1px solid #808080;
            background-color: #808080;
            display: none;
            border-radius: 5px 5px; /*设置圆角*/
        }
        td {
            text-align: center;
        }
        #fgDiv input {
            align-items: center;
        }
    </style>
</head>
<body>
<input style="position:fixed;left:500px;" type="button" value="添加" id="btnAdd" />
<table style="position:fixed;top:40px;" id="list" border="1" cellspacing="0" cellpadding="0">
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>地址</th>
        <th>电话</th>
        <th>操作</th>
    </tr>
</table>
<div id="bgDiv"></div>

<div id="fgDiv">
    <input type="hidden" id="hidId" />
    姓名：<input type="text" id="name" />
    <br>
    地址：<input type="text" id="address" />
    <br>
    电话：<input type="text" id="phone" />
    <br>
    <input type="button" id="btnSave" value="保存" />
</div>
</body>
</html>
<script src="/js/jquery.min.js"></script>
<script>
    // 查询整个列表
    function queryAll() {
        clearTable();
        $.ajax({
            url: '/resume/queryAll',
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: function (data) {
                //生成表格数据
                $.each(data, function (index, item) {
                    $('<tr id="tr' + item.id + '"><td>'
                        + item.id + '</td><td>'
                        + item.name + '</td><td>'
                        + item.address + '</td><td>'
                        + item.phone + '</td><td><input type="button" value="修改"  onclick="update('+item.id+')"/>'
                        +'<input type="button" value="删除" onclick="del('+item.id+')"/></td></tr>').appendTo($("#list"));
                });
            }
        })
    }
    function clearTable(){
        const table = document.getElementById("list");
        const rows = table.rows.length;
        //清空表中的行和列
        for(let a=1; a<rows; a++){
            $("table tr").eq(1).remove();
        }
    }
    $(function () {
        queryAll();
        //添加
        $("#btnAdd").click(function () {
            //显示添加界面
            $("#bgDiv").css("display", "block").css("width", window.innerWidth + "px").height(window.innerHeight + "px");
            $("#fgDiv").css("display", "block").css("left", (window.innerWidth - 250) / 2 + "px").css("top", (window.innerHeight - 100) / 2 + "px");

            //打开的清除文本框中的数据
            //$("#fgDiv input[type=text]").val('');
            $("#fgDiv :text,:hidden").val('');
        });
        //保存
        $("#btnSave").click(function () {
            const data = JSON.stringify({
                "id":$("#hidId").val(),
                "name":$("#name").val(),
                "address":$("#address").val(),
                "phone":$("#phone").val(),
            })
            $.ajax({
                url: '/resume/save',
                type: 'POST',
                data:data,
                contentType: 'application/json;charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    if(data){
                        queryAll();
                    }
                }
            })

            //隐藏界面
            $("#bgDiv").css("display", "none");
            $("#fgDiv").css("display", "none");
        });
    })
    function update(id){
        //显示添加界面
        $("#bgDiv").css("display", "block").css("width", window.innerWidth + "px").height(window.innerHeight + "px");
        $("#fgDiv").css("display", "block").css("left", (window.innerWidth - 250) / 2 + "px").css("top", (window.innerHeight - 100) / 2 + "px");
        const tds = $("#tr" + id).children('td');
        //设置文本框的值
        $("#hidId").val(tds[0].innerHTML);//隐藏域存放修改之前的行的ID编号值
        $("#name").val(tds[1].innerHTML);
        $("#address").val(tds[2].innerHTML);
        $("#phone").val(tds[3].innerHTML);
    }
    function del(id) {
        $.ajax({
            url: '/resume/delete',
            type: 'POST',
            data:JSON.stringify({
                "id":id,
            }),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: function (data) {
                if(data){
                    queryAll();
                }
            }
        })
    }
</script>