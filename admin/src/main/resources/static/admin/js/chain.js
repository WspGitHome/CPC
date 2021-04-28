/*联动*/
function initChain() {
    $("#libType").change(function () {
        $("#pid").show();
        $("#pid").nextAll().remove();//清除
        var libTypeId = $(this).val();
        var param = {"type": libTypeId};
        if ($("#templateFlag").val() == 'template') {
            param = {"type": libTypeId, "templateFlag": 1};
        }
        Core.postAjax("/library/listAll", param, function (data) {
            var html = "<option  value=''>选择数据库</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
            }
            $("#libraryId").html(html);
        })
    });
    $("#libraryId").change(function () {
        $("#pid").show();
        $("#pid").nextAll().remove();//清除
        $("#pid-form").val(0);
        var library = $(this).val();
        var $txtCategory = $("#pid");
        if ($txtCategory != undefined) {
            BindCategory(library);
        }
    });
}


function BindCategory(library) {
    Core.postAjax("/column/tree", {"library": library}, function (data) {
        buildTree(data);
    });
};

var r = /^\+?[1-9][0-9]*$/;　　//判断是否为正整数
function buildTree(data) {
    $("#pid").hide();
    $("#pid").nextAll().remove();//清除
    var $txtCategory = $("#pid");
    var $pidForm = $("#pid-form");
    var _selectId = ["selBigClass", "selSmallClass", "selThreeClass"];
    for (var i = 0; i < _selectId.length; i++) {
        var select = document.createElement("select");
        select.id = _selectId[i];
        select.name = _selectId[i];
        select.className = "form-control m-2";
        select.style = "width:200px;";
        $txtCategory.parent().append(select);
    }
    var $selBigClass = $("#selBigClass");
    var $selSmallClass = $("#selSmallClass");
    var $selThreeClass = $("#selThreeClass");
    $selSmallClass.hide();
    $selThreeClass.hide();

    var holder = [];
    var html = ['<option selected>一级栏目</option>'];
    for (var i = 0; i < data.length; i++) {
        if (data[i].pid == 0) {
            if (data[i].selected) {
                html.push('<option value="' + data[i].id + '" selected ">' + data[i].name + '</option>');
            } else {
                html.push('<option value="' + data[i].id + '">' + data[i].name + '</option>');
            }
        }
        if (data[i].pid != 0 && data[i].selected == true) {
            holder.push(data[i]);
        }
    }
    //第一级类别
    $selBigClass.empty().append(html.join('')).change(function () {
        if (r.test(this.value)) {
            $pidForm.val(this.value);
        } else {
            $pidForm.val(0);
        }
        $selThreeClass.hide();
        $selSmallClass.show();
        var html = ['<option selected>二级栏目</option>'];
        for (var i = 0; i < data.length; i++) {
            if (data[i].pid == $("#selBigClass").val() && $("#selBigClass").val() != '') {
                if (data[i].selected) {
                    html.push('<option value="' + data[i].id + '" selected ">' + data[i].name + '</option>');
                } else {
                    html.push('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
            }
        }
        //第二级类别
        $selSmallClass.empty().append(html.join('')).change(function () {
            if (r.test(this.value)) {
                $pidForm.val(this.value);
            } else {
                $pidForm.val($selBigClass.val());
            }
            $selThreeClass.show();
            var html = ['<option selected>三级栏目</option>'];
            for (var i = 0; i < data.length; i++) {
                if (data[i].pid == $selSmallClass.val() && $selSmallClass.val() != '') {
                    if (data[i].selected) {
                        html.push('<option value="' + data[i].id + '" selected ">' + data[i].name + '</option>');
                    } else {
                        html.push('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                    }
                }
            }
            //第三级类别
            $selThreeClass.empty().append(html.join('')).change(function () {
                if (r.test(this.value)) {
                    $pidForm.val(this.value);
                } else {
                    $pidForm.val($selSmallClass.val());
                }
            });
            //如果不存在第三级类别则隐藏第三级类别控件
            if ($selThreeClass.get(0).options.length <= 1) {
                $selThreeClass.hide();
                return false;
            }
        });
        //如果不存在第二级类别则隐藏第二级类别控件
        if ($selSmallClass.get(0).options.length <= 1) {
            $selSmallClass.hide();
            return false;
        }

    });

    var holder1 = [];
    if (holder.length > 0) {
        $selThreeClass.hide();
        $selSmallClass.show();
        var html = ['<option selected>二级栏目</option>'];
        for (var i = 0; i < data.length; i++) {
            if (data[i].pid == $("#selBigClass").val() && $("#selBigClass").val() != '') {
                if (data[i].selected) {
                    html.push('<option value="' + data[i].id + '" selected ">' + data[i].name + '</option>');
                } else {
                    html.push('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
            }
            if (data[i].pid != 0 && data[i].pid != $("#selBigClass").val() && data[i].selected == true) {
                holder1.push(data[i]);
            }
        }
        //第二级类别
        $selSmallClass.empty().append(html.join('')).change(function () {
            if (r.test(this.value)) {
                $pidForm.val(this.value);
            } else {
                $pidForm.val($selBigClass.val());
            }
            $selThreeClass.show();
            var html = ['<option selected>三级栏目</option>'];
            for (var i = 0; i < data.length; i++) {
                if (data[i].pid == $selSmallClass.val() && $selSmallClass.val() != '') {
                    if (data[i].selected) {
                        html.push('<option value="' + data[i].id + '" selected ">' + data[i].name + '</option>');
                    } else {
                        html.push('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                    }
                }
            }
            //第三级类别
            $selThreeClass.empty().append(html.join('')).change(function () {
                if (r.test(this.value)) {
                    $pidForm.val(this.value);
                } else {
                    $pidForm.val($selSmallClass.val());
                }
            });
            //如果不存在第三级类别则隐藏第三级类别控件
            if ($selThreeClass.get(0).options.length <= 1) {
                $selThreeClass.hide();
                return false;
            }
        });


    }
    if (holder1.length > 0) {
        $selThreeClass.show();
        var html = ['<option selected>三级栏目</option>'];
        for (var i = 0; i < data.length; i++) {
            if (data[i].pid == $selSmallClass.val() && $selSmallClass.val() != '') {
                if (data[i].selected) {
                    html.push('<option value="' + data[i].id + '" selected ">' + data[i].name + '</option>');
                } else {
                    html.push('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
            }
        }
        //第三级类别
        $selThreeClass.empty().append(html.join('')).change(function () {
            if (r.test(this.value)) {
                $pidForm.val(this.value);
            } else {
                $pidForm.val($selSmallClass.val());
            }
        });

    }
}
