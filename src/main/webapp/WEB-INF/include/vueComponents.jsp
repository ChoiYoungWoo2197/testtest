<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>

<%--[ 비동기 사용자 검색&다중 선택 ]----------------------------------------------------------------------------------%>
<%--------------------------------------------------------------------------------------------------------------------%>

<script>
	Vue.component('select-user', {
		props: ["origin-users"],
		template: '#select-user-template',
		data: function() {
			return {
				keyword: "",
				minKeywordLength: 2,
				users: [],
				searchList: [],
				mouseIn: false
			}
		},
		mounted: function () {
			var vm = this;

			$(this.$el).on("mouseenter", function (event) {
				vm.mouseIn = true;
			}).on("mouseleave", function (event) {
				vm.mouseIn = false;
			});

			this.propsToUsers();
		},
		watch: {
			originUsers: function (newUserKeys, oldUserKeys) {
				this.propsToUsers();
			}
		},
		methods: {
			search: function (event) {

				if (this.keyword.length < this.minKeywordLength) {
					return false;
				}

				var vm = this;

				$.ajax({
					method: "GET",
					url: "/common/user/keyword/search.do",
					data: { keyword: vm.keyword },
					dataType: "json"
				})
				.done(function( response ) {
					vm.searchList = response.userList;
				});

			},
			onFocusoutKeywordField: function (event) {
				if (this.mouseIn) {
					return false;
				}
				this.reset();
			},
			reset: function () {
				this.keyword = "";
				this.searchList = [];
			},
			addUser: function (addedUser) {
				this.users.push(addedUser);
				this.reset();
			},
			removeUser: function (removedUser) {
				this.users.splice(this.users.indexOf(removedUser), 1)
			},
			propsToUsers: function () {

				if (this.originUsers.length === 0) {
					this.users = [];
					return false;
				}

				var vm = this;

				$.ajax({
					method: "GET",
					url: "/common/user/keys/search.do",
					data: { ids: vm.originUsers },
					dataType: "json"
				})
				.done(function( response ) {
					vm.users = response.userList;
					vm.$forceUpdate();
				});
			}
		}
	})
</script>

<script type="text/x-template" id="select-user-template">

	<div class="vm-select-user">

		<div class="vm-select-user__search mt-3 mb-3" style="position: relative;">
			<input class="vm-select-user__search-input" type="text" placeholder="검색어를 입력하세요."
				   v-bind:value="keyword"
				   v-on:input="keyword = $event.target.value"
				   v-on:keyup="search()"
				   v-on:keydown.enter.prevent=""
				   v-on:focusout="onFocusoutKeywordField($event)">

			<div v-if="keyword != '' && searchList.length != 0" class="vm-select-user__search-list border mt-3 mb-3">
				<ul class="">
					<li class="border-b" v-for="user in searchList" v-bind:key="user.uumUsrKey" v-on:click="addUser(user)">
						{{ user.uumUsrNam }} ({{ user.udmDepNam }})
					</li>
				</ul>
			</div>
		</div>

		<div class="vm-select-user__selected-list talbeArea">
			<table>
				<tr class="border-b">
					<th width="20%">아이디</th>
					<th width="20%">이름</th>
					<th width="20%">부서</th>
					<th width="20%">직급</th>
					<th width="20%"></th>
				</tr>
				<tr v-for="user in users" v-bind:key="user.uumUsrKey" class="border-b">
					<td>{{ user.uumUsrId }}</td>
					<td>{{ user.uumUsrNam }}</td>
					<td>{{ user.division }}</td>
					<td>{{ user.pos }}</td>
					<td>
						<button v-on:click="removeUser(user)"><span class="icoDel"></span> 삭제</button>
					</td>
				</tr>
			</table>
		</div>

	</div>

</script>

<style>
	.vm-select-user__search-list {
		max-height: 200px;
		overflow-y: scroll;
		position: absolute;
		background-color: #fff;
		width: 547px;
		padding: 0.5rem 10px;
		z-index: 999;
		cursor: pointer;
	}

	.vm-select-user__search-list ul li {
		padding: 1rem 0;
	}

	.vm-select-user__search-input {
		border: 1px solid #e2e8f0;
		width: 597px;
		padding: 0.5rem 10px;
	}

	.vm-select-user__selected-list table th {
		text-align: left;
	}
</style>

<%--[ 비동기 파일 매니저 ]--------------------------------------------------------------------------------------------%>
<%--------------------------------------------------------------------------------------------------------------------%>

<script>

	Vue.component('file-manager', {
		props: ["tbl-gbn", "con-gbn", "con-key"],
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
					url: "/protectionMeasure/task/file/list.do",
					data: { id: vm.conKey },
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
			isChanged: function () {
				return ! ( this.addedFiles.length === 0 && this.deletedFiles.length === 0 );
			},
			saveFiles: function () {
				var vm = this;

				if ( this.addedFiles.length === 0 && this.deletedFiles.length === 0 ) {
					alert("변경된 파일 정보가 없습니다.");
					return false;
				}

				var formData = new FormData();
				formData.append("tblGbn", this.tblGbn);
				formData.append("conGbn", this.conGbn);
				formData.append("conKey", this.conKey);

				if ( this.deletedFiles.length > 0 ) {
					for (var i = 0; i < this.deletedFiles.length; ++i) {
						formData.append("deletedFiles[]", this.deletedFiles[i].umf_fle_key)
					}
				}

				if ( this.addedFiles.length > 0 ) {
					for (var i = 0; i < this.addedFiles.length; i++) {
						formData.append("addedFiles[]", this.addedFiles[i])
					}
				}

				$.ajax({
					method: "POST",
					url: "/protectionMeasure/task/file/update.do",
					enctype: 'multipart/form-data',
					data: formData,
					processData: false,
					contentType: false,
					async: false
				})
				.done(function( response ) {
					console.log("saveFiles Done", response);
					// vm.getFiles();
					alert("파일이 수정되었습니다.");
				})
				.always(function () {
					// vm.initModifiedFile();
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
			},
			resetFileUploader: function () {
				this.$refs.fileUploader.value = '';
			}

		}
	})

</script>

<script type="text/x-template" id="file-manager-template">

	<div class="file-manager-vm">
		<div class="border-b pb-3 mb-5">
			<input ref="fileUploader" v-on:click="resetFileUploader" v-on:change="addFiles($event)" name="addedFiles[]" type="file" class="mb-3" multiple>
			<p>※ Ctrl 키를 누른 상태에서 파일을 클릭하면 한번에 여러개의 파일을 선택할 수 있습니다.</p>
		</div>
		<div class="mb-5 border-b pb-5">
			<span class="font-bold mb-3">저장된 파일</span>
			<p v-if="files.length < 1">
				저장된 파일 없음
			</p>
			<ul v-else>
				<li v-for="file in files">
					<a v-bind:href="'/common/getFileDown.do?downKey=' + file.umf_fle_key">{{ file.umf_con_fnm }}</a>
					<span class="cursor-pointer ml-3 text-red-300" v-on:click="deleteFile(file)">삭제</span>
				</li>
			</ul>
		</div>
		<div class="mb-5 border-b pb-5">
			<span class="font-bold mb-3">추가될 파일</span>
			<p v-if="addedFiles.length < 1">
				추가될 파일 없음
			</p>
			<ul v-else>
				<li v-for="file in addedFiles">
					{{ file.name }}
					<span class="cursor-pointer ml-3 text-red-300" v-on:click="addedFiles.splice(addedFiles.indexOf(file), 1)">삭제</span>
				</li>
			</ul>
		</div>
		<div>
			<span class="font-bold mb-3">삭제될 파일</span>
			<p v-if="deletedFiles.length < 1">
				삭제될 파일 없음
			</p>
			<ul v-else>
				<li v-for="file in deletedFiles">
					{{ file.umf_con_fnm }}
					<span class="cursor-pointer ml-3 text-red-300" v-on:click="cancelDeleteFile(file)">삭제</span>
				</li>
			</ul>
		</div>
	</div>

</script>

<style>

</style>

<%--[ 비동기 파일  목록 ]--------------------------------------------------------------------------------------------%>
<%--------------------------------------------------------------------------------------------------------------------%>

<script>

	Vue.component('file-list', {
		props: ["tbl-gbn", "con-gbn", "con-key"],
		template: '#file-list-template',
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
					url: "/protectionMeasure/task/file/list.do",
					data: { id: vm.conKey },
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
			isChanged: function () {
				return ! ( this.addedFiles.length === 0 && this.deletedFiles.length === 0 );
			},
			saveFiles: function () {
				var vm = this;

				if ( this.addedFiles.length === 0 && this.deletedFiles.length === 0 ) {
					alert("변경된 파일 정보가 없습니다.");
					return false;
				}

				var formData = new FormData();
				formData.append("tblGbn", this.tblGbn);
				formData.append("conGbn", this.conGbn);
				formData.append("conKey", this.conKey);

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
					url: "/protectionMeasure/task/file/update.do",
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

<script type="text/x-template" id="file-list-template">

	<div class="file-manager-vm">
		<ul>
			<li v-for="(file, index) in files">
				<a v-bind:href="'/common/getFileDown.do?downKey=' + file.umf_fle_key">
					{{ index + 1 }}. {{ file.umf_con_fnm }}
				</a>
			</li>
		</ul>
	</div>

</script>