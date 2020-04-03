<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>

<%--[ 비동기 파일 매니저 ]--------------------------------------------------------------------------------------------%>
<%--------------------------------------------------------------------------------------------------------------------%>

<script>

    Vue.component('file-manager', {
        props: ["tbl-gbn", "con-gbn", "con-key", "list-url", "save-url"],
        template: '#file-manager-template',
        data: function () {
            return {
                // 저장되어 있던 파일들
                files: [],
                // 추가된 파일들
                addedFiles: [],
                // 삭제된 파일들
                deletedFiles: []
            }
        },
        mounted: function () {
            this.initData();
        },
        watch: {
            tblGbn: function (newTblGbn, oldTblGbn) {
                this.initData();
            },
            conGbn: function (newTblGbn, oldTblGbn) {
                this.initData();
            },
            conKey: function (newTblGbn, oldTblGbn) {
                this.initData();
            }
        },
        methods: {

            getFiles: function () {
                var vm = this;

                if ( ! this.isValidProps()) {
                    console.log("props 값이 유효하지 않습니다.");
                    return false;
                }

                $.ajax({
                    method: "GET",
                    url: vm.listUrl,
                    data: { umf_tbl_gbn: vm.tblGbn, umf_con_gbn: vm.conGbn, umf_con_key: vm.conKey },
                    dataType: "json"
                })
                    .done(function( response ) {
                        console.log("getFiles Done", response);
                        vm.files = response.files;
                        // vm.$forceUpdate();
                    });
            },
            addFiles: function (event) {
                var vm = this;
                var $input = $(event.target);
                _.forEach($input.prop("files"), function (file) {
                    console.log(file);
                    vm.addedFiles.push(file)
                });
            },
            deleteFile: function (file) {
                this.files.splice(this.files.indexOf(file), 1);
                this.deletedFiles.push(file);
            },
            cancelDeleteFile: function (file) {
                this.deletedFiles.splice(this.deletedFiles.indexOf(file), 1);
                this.files.push(file);
            },
            saveFiles: function () {
                var vm = this;

                if ( this.addedFiles.length === 0 && this.deletedFiles.length === 0 ) {
                    alert("변경된 파일 정보가 없습니다.");
                    return false;
                }

                var formData = new FormData();
                formData.append("umf_tbl_gbn", this.tblGbn);
                formData.append("umf_con_gbn", this.conGbn);
                formData.append("umf_con_key", this.conKey);

                if ( this.deletedFiles.length > 0 ) {
                    for (var i = 0; i < this.deletedFiles.length; ++i) {
                        formData.append("deletedFiles[]", this.deletedFiles[i].umf_fle_key)
                    }
                }

                if ( this.addedFiles.length > 0 ) {
                    for (var i = 0; i < this.addedFiles.length; i++) {
                        formData.append("addedFiles[" + i + "]", this.addedFiles[i])
                    }
                }

                $.ajax({
                    method: "POST",
                    url: vm.saveUrl,
                    enctype: 'multipart/form-data',
                    data: formData,
                    processData: false,
                    contentType: false
                })
                    .done(function( response ) {
                        console.log("saveFiles Done", response);
                        vm.getFiles();
                        alert("파일이 수정되었습니다.");
                    })
                    .always(function () {
                        vm.initModifiedFile();
                    });

            },
            isValidProps: function () {
                return (this.tblGbn != null && this.conGbn != null && this.conKey != null);
            },
            initData: function () {
                this.getFiles();
                this.initModifiedFile();
            },
            initModifiedFile: function () {
                this.addedFiles.splice(0, this.addedFiles.length);
                this.deletedFiles.splice(0, this.deletedFiles.length);
            }

        }
    })

</script>

<script type="text/x-template" id="file-manager-template">

    <div class="file-manager-vm">
        <div class="border-b pb-3 mb-3">
            <div class="input-group mr-3 mb-2">
                <div class="custom-file">
                    <input v-on:change="addFiles($event)" multiple type="file" class="custom-file-input" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01">
                    <label class="custom-file-label" for="inputGroupFile01" data-browse="찾기">파일을 선택하세요.</label>
                </div>
            </div>
        </div>
        <div class="mb-3 pb-3">
            <p v-if="files.length < 1 && addedFiles.length < 1">
                저장된 파일 없음
            </p>
            <ul v-if="addedFiles.length > 0">
                <li v-for="file in addedFiles" style="color: red">
                    {{ file.name }}
                    <span class="cursor-pointer ml-3 delete-btn" v-on:click="addedFiles.splice(addedFiles.indexOf(file), 1)">삭제</span>
                </li>
            </ul>
            <ul v-if="files.length > 0">
                <li v-for="file in files">
                    <a v-bind:href="'/common/getFileDown.do?downKey=' + file.umf_fle_key">{{ file.umf_con_fnm }}</a>
                    <span class="cursor-pointer ml-3 delete-btn" v-on:click="deleteFile(file)">삭제</span>
                </li>
            </ul>
            <button class="btn btn-outline-secondary btn-block btn-sm" v-on:click.prevent="saveFiles()">저장</button>
        </div>
    </div>

</script>

<style>
    .delete-btn {
        color: #e0002a;
    }
</style>