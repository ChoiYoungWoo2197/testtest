<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.uwo.isms.domain.enums.ProtectionMeasureTaskStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<link rel="stylesheet" href="/common/css/jstree/style.min.css">
	<style>
		.input-group {
			margin-bottom: 1rem;
		}
		.input-group .input-group__title {
			color: #888;
			margin-bottom: 0.5rem;
		}

		.card {
			border-top: 2px solid #ccc;
		}
		.card-header {
			padding: 1rem;
			background-color: #eee;
			overflow: auto;
		}
		.card-header > button {
			float: right;
		}
		.card-header > h1 {
			font-size: 14px;
			font-weight: bold;
			float: left;
		}
		.card-body {
			padding: 1rem 0;
		}
		th, td {
			padding: 0.5rem;
		}
		#protection-measure-task .inputText, #protection-measure-task .inputTextarea, #protection-measure-task .selectBox {
			width: 597px;
			padding: 0.5rem 10px;
		}
		.inputTextarea {
			border: 1px solid #ddd;
		}
		#content-box {
			margin-bottom: 100px;
		}

		.jstree-anchor, .jstree-anchor:link, .jstree-anchor:visited, .jstree-anchor:hover, .jstree-anchor:active {
			color: #999;
			font-weight: normal;
		}
		.jstree-anchor.permitted-node {
			color: #666;
			font-weight: bold;
		}
		.task-section .talbeArea td {
			text-align: left;
			padding: 8px;
		}
		.vm-select-user__search-input, .vm-select-user__search-list {
			width: 609px !important;
		}
		#protection-measure-menus {
			padding: 0.5rem;
		}
		.jstree-menus { position: relative; overflow: auto }
		.jstree-menus li {
			float: left;
			margin-right: 0.5rem;
		}
        #protection-measure-task-tree-section {
			width: 350px;
			min-height: 531px;
            overflow: scroll;
        }
	</style>
	<script type="application/javascript" src="/common/js/jstree.js"></script>
	<script type="application/javascript" src="/common/js/vue.js"></script>
	<script type="application/javascript" src="/common/js/moment.js"></script>
	<script type="application/javascript" src="/common/js/helpers.js"></script>
	<%@ include file="/WEB-INF/include/vueComponents.jsp"%>
	<script>

		$.ajaxSetup({ cache: false });

		function openPopUp(url) {
			var win = window.open(url, "TEST", "width=730, height=800, menubar=no, location=no, status=no, scrollbars=yes");
			win.focus();
		}
		
		var protectionMeasureTaskTree = null;
		var protectionMeasureTaskVue = null;
		var protectionMeasureMenuVue = null;

		var ProtectionMeasureTaskTree = function (data) {

			this.defaultData = {
				mode: "WORKER",
				loggedInUser: null,
				protectionMeasure: {},
				protectionMeasureTaskStatus : [],
				treeElementId: "",
				treeInstance: null
			};

			this.data = $.extend(true, this.defaultData, data);
		};

		ProtectionMeasureTaskTree.prototype.isManager = function () {
			return this.data.mode === "MANAGER";
		};

		ProtectionMeasureTaskTree.prototype.hasPermissionOfNode = function (node) {

			if (node.type === "default" && this.isManager()) return true;

			if (node.original === undefined || node.original.workerKeysCascade === undefined)
				return false;

			// 관리자
			if (this.isManager()) return true;

			var workers = node.original.workerKeysCascade.split(",");

			return workers.indexOf( typeof this.data.loggedInUser === "number" ?
					this.data.loggedInUser.toString() : this.data.loggedInUser ) > -1;

		};

		ProtectionMeasureTaskTree.prototype.getTreeTaskList = function () {
			var allNodes = [];
			var nodesForJson = this.treeInstance.get_json("#", {flat: true});
			for (var i = 0; i < nodesForJson.length; i++) {
				var nodeForJson = nodesForJson[i];
				var node = this.treeInstance.get_node(nodeForJson.id);
				allNodes.push(node);
			}
			return allNodes;
		};

		ProtectionMeasureTaskTree.prototype.getMenus = function ($node) {

			var that = this;

			var tree = $("#" + that.data.treeElementId).jstree(true);
			var menus = {};

			/*분류 생성하기*/
			if ($node.type === 'default' && that.hasPermissionOfNode($node)) {
				$.extend(menus, {
					"createCATEGORI1": {
						"separator_before": false,
						"separator_after": false,
						"label": "분류 생성하기",
						"action": function (obj) {
							$node = tree.create_node($node, {"text" : "분류명", "type": "CATEGORY1"});
							tree.edit($node);
						}
					}
				})
			}
			/*중점과제 생성하기*/
			if ($node.type === 'CATEGORY1' && that.hasPermissionOfNode($node)) {
				$.extend(menus, {
					"EnvironmentCreate": {
						"separator_before": false,
						"separator_after": false,
						"label": "중점과제 생성하기",
						"action": function (obj) {
							$node = tree.create_node($node, {"text" : "중점과제 제목", "type": "TASK"});
							tree.edit($node);
						}
					}
				})
			}
			/*세부업무 생성하기*/
			/*
            if ($node.type === 'TASK' && that.hasPermissionOfNode($node)) {
                $.extend(menus, {
                    "AttributeKeyCreate": {
                        "separator_before": false,
                        "separator_after": false,
                        "label": "세부업무 생성하기",
                        "action": function (obj) {
                            $node = tree.create_node($node, {"text" : "세부업무 제목", "type": "MICRO_TASK"});
                            console.log(obj, $node);
                            tree.edit($node);
                        }
                    }
                })
            }
            */
			/*삭제하기*/
			if ($node.children.length === 0) {

				if ($node.type !== "CATEGORY1" && that.hasPermissionOfNode(that.treeInstance.get_node($node.parent))
						|| $node.type === "CATEGORY1" && that.hasPermissionOfNode($node)) {

					$.extend(menus, {
						"Remove": {
							"separator_before": false,
							"separator_after": false,
							"label": "삭제하기",
							"action": function (obj) {
								tree.delete_node($node);
							}
						}
					});

				}

			}
			/*이름변경*/
			if (["CATEGORY1", "TASK", "MICRO_TASK"].includes($node.type) && that.hasPermissionOfNode($node)) {

				$.extend(menus, {
					"Rename": {
						"separator_before": false,
						"separator_after": false,
						"label": "이름변경",
						"action": function (obj) {
							tree.edit($node)
						}
					}
				});

			}

			return menus;

		};

		ProtectionMeasureTaskTree.prototype.makeMenus = function ($node) {
			var menus = this.getMenus($node);
			console.log("makeMunus");
			console.log(menus);
			protectionMeasureMenuVue.menus = menus;
		};

		ProtectionMeasureTaskTree.prototype.makeTree = function () {

			var that = this;

			$("#" + this.data.treeElementId)
				.on("create_node.jstree", function (event, created_node) {

					console.log("EVENT: created_node", created_node);

					$.ajax({
						method: "POST",
						url: "/inspt/FM-INSPT005_TASK_STORE.do",
						dataType: "json",
						data: {
							protectionMeasureId : protectionMeasureTaskTree.data.protectionMeasure.id,
							parentNodeId: created_node.parent === "#" ? null : created_node.parent,
							title: created_node.node.text,
							nodeType: created_node.node.type
						}
					}).done(function( response ) {
						var createdProtectionMeasureTask = response.task;
						created_node.instance.set_id(created_node.node, createdProtectionMeasureTask.id);
						created_node.node.original = createdProtectionMeasureTask;
						created_node.instance.refresh();
					});

				})
				.on("rename_node.jstree", function (event, renamed_node) {
					console.log("EVENT: rename_node", renamed_node);

					var node    = renamed_node.node;
					var text    = renamed_node.text;
					var old     = renamed_node.old;

					$.ajax({
						method: "POST",
						url: "/inspt/FM-INSPT005_TASK_UPDATE.do",
						dataType: "json",
						data: {
							id: node.id,
							title: text
						}
					}).done(function (response) {
						renamed_node.node.original = response.task;
						renamed_node.instance.refresh();

						var selected_node = renamed_node;

						protectionMeasureTaskVue.id = selected_node.node.id;
						protectionMeasureTaskVue.treeTask = selected_node.node;
						protectionMeasureTaskVue.treeTaskList = that.getTreeTaskList();
					})

				})
				.on("delete_node.jstree", function (event, deleted_node) {

					console.log("EVENT: delete_node", deleted_node);

					var node    = deleted_node.node;

					$.ajax({
						method: "POST",
						url: "/inspt/FM-INSPT005_TASK_DELETE.do",
						dataType: "json",
						data: {
							id: node.id
						}
					}).done(function (response) {
						deleted_node.instance.refresh();
					})
				})
				.on("select_node.jstree", function (event, selected_node) {
					console.log("EVENT: select_node [" + selected_node.node.id + "]", selected_node.node);

					protectionMeasureTaskVue.id = selected_node.node.id;
					protectionMeasureTaskVue.treeTask = selected_node.node;
					protectionMeasureTaskVue.treeTaskList = that.getTreeTaskList();

					that.makeMenus(selected_node.node);
				})
				.jstree({
					"core" : {
						"animation" : 0,
						"themes" : { "stripes" : true },
						"data" : {
							"method" : "GET",
							'url' : function (node) {
								return "/inspt/FM-INSPT005_TASK_LIST.do";
							},
							'data' : function (node) {
								return { season: protectionMeasureTaskTree.data.protectionMeasure.season };
							},
							"async" : false,
							"dataFilter" : function (rawData, type) {
								if (type !== "json")
									console.warn("json 타입 응답이 아닙니다.");

								var data = JSON.parse(rawData);
								var taskList = data.taskList;

								if (taskList === undefined)
									console.warn("중점과제 업무 목록정보가 없습니다.");

								_.each(taskList, function (task) {
									task.type = task.nodeType;
									task.text = task.title;
									task.parent = task.parentNodeId;
									task.a_attr = { "class" : that.hasPermissionOfNode({original: task}) ? "permitted-node" : "" }
								});

								taskList.push({id: 0, text: protectionMeasureTaskTree.data.protectionMeasure.title, parent: "#", type: "default"});

								console.log("taskList", taskList);

								return taskList;
							}
						},
						"check_callback": function (operation, node, node_parent, node_position, more) {
							// operation can be 'create_node', 'rename_node', 'delete_node', 'move_node', 'copy_node' or 'edit'
							// in case of 'rename_node' node_position is filled with the new node name
							if (operation === 'delete_node' && ! confirm("삭제하시겠습니까?"))
								return false;

							return true;
						}
					},
					'force_text' : true,
					"types" : {
						"default" : {
							"icon" : "fa fa-shield",
							"valid_children" : ["CATEGORY1"]
						},
						"CATEGORY1" : {
							"icon" : "fa fa-folder-o",
							"valid_children" : ["TASK"]
						},
						"TASK" : {
							"icon" : "fa fa-file-text-o",
							"valid_children" : ["MICRO_TASK"]
						},
						"MICRO_TASK" : {
							"icon" : "fa fa-check-square-o",
							"valid_children" : []
						}
					},
					"plugins" : [
						"contextmenu", "state", "types", "wholerow"
					],
					"contextmenu": {
						"items": function ($node) {

							var menus = that.getMenus($node);

							return menus;

						}
					},
				})

			this.treeInstance = $("#" + this.data.treeElementId).jstree(true);

		};

		Vue.filter('dateFormat', function (value) {
			return value == "" ? "" : moment(value).format("YYYY-MM-DD")
		});
		Vue.filter('dateFormatKor', function (value) {
			return value == "" ? "" : moment(value).format("YYYY년 MM월 DD일")
		});

		$(document).ready(function () {

			protectionMeasureTaskTree = new ProtectionMeasureTaskTree({
				treeElementId : "protection-measure-task-tree",
				mode: "${mode}",
				loggedInUser: ${loginVO.getuum_usr_key()},
				protectionMeasure: ${protectionMeasureJson},
				protectionMeasureTaskStatus : [
					<% for (ProtectionMeasureTaskStatus status: ProtectionMeasureTaskStatus.values()) { %>
					{ title: "<%=status.getTitle()%>", value: "<%=status%>" },
					<% } %>
				]
			});

			protectionMeasureTaskTree.makeTree();

			protectionMeasureMenuVue = new Vue({
				el: "#protection-measure-menus",
				data: {
					menus: {}
				},
				methods: {
					menuAction: function (menu) {
						menu.action();
					}
				}
			});

			protectionMeasureTaskVue = new Vue({
				el: '#protection-measure-task',
				data: {
					user: protectionMeasureTaskTree.data.loggedInUser,
					mode: protectionMeasureTaskTree.data.mode,
					id: 0,
					task: {},
                    treeTask: null,
                    treeTaskList: [],
					worker: [],
					taskStatus: protectionMeasureTaskTree.data.protectionMeasureTaskStatus
				},
				updated: function () {

					// 기준일
					$("[data-datepicker]").datepicker({
						onSelect: function(dateText) {
							$(this)[0].dispatchEvent(new Event('input', { 'bubbles': true }))
						}
					});

				},
				watch: {
					id: function (newId, oldId) {

						console.log("changed vue id", newId, oldId);

						var vm = this;

						if (newId < 1) {
							this.task = {id: newId, title: protectionMeasureTaskTree.data.protectionMeasure.title};
							this.worker = [];
						} else {
							this.getTask(newId, function(response) {
								if (vm.$refs.selectWorker) {
									// vm.$refs.selectWorker.propsToUsers();
								}
							});
						}

					},
					treeTask: function (newTreeTask, oldTreeTask) {
						console.log("changed vue treeTask", newTreeTask, oldTreeTask);
						this.task.title = newTreeTask.original.title;
					},
					treeTaskList: function (newTreeTaskList, oldTreeTaskList) {
						console.log("changed vue treeTaskList", newTreeTaskList, oldTreeTaskList)
					}
				},
				computed: {
					workerKeys: function () {
						return _.flatMap(this.worker, function (worker) {
							return worker.uumUsrKey;
						})
					},
					doShowWorkerSection: function () {
						return this.hasWorkerSectionPermission()
					}
				},
				methods: {
					getTask: function (id, callback) {

						var vm = this;

						if (id < 1) {
							return false;
						}

						console.log("getTask", vm.id);

						$.ajax({
							method: "GET",
							url: "/inspt/FM-INSPT005_TASK_SHOW.do",
							data: { id: vm.id },
							dataType: "json"
						})
						.done(function( response ) {
							vm.task = response.task;
							vm.worker = response.workerList;
							if (callback != undefined) { callback(response) }
						});
					},
					saveTask: function () {

						if (this.task.nodeType !== "TASK" && this.task.nodeType !== "MICRO_TASK") {
							return false;
						}

						this.storeTask();

					},
					storeTask: function () {

						var vm = this;

						$.ajax({
							method: "POST",
							url: "/inspt/FM-INSPT005_TASK_UPDATE.do",
							data: vm.task,
							dataType: "json"
						})
						.done(function( response ) {
							// response.task;
							alert("[" + vm.task.title + "] 내용이 수정되었습니다.");
							protectionMeasureTaskTree.treeInstance.refresh();
						});


					},
					/*담당자*/
					saveWorker: function () {
						var oldWorkerKeys = this.workerKeys;
						var newWorkerKeys = _.flatMap(this.$refs.selectWorker.users, function (worker) { return worker.uumUsrKey });

						if ( _.difference(oldWorkerKeys, newWorkerKeys).length !== 0
								|| _.difference(newWorkerKeys, oldWorkerKeys).length !== 0) {

							this.storeTaskWorker(this.id, newWorkerKeys);

						} else {
							alert("[" + this.task.title + "] 변경된 담당자 정보가 없습니다.");
						}
					},
					storeTaskWorker: function (taskId, workerKeys) {

						var vm = this;

						$.ajax({
							method: "POST",
							url: "/inspt/FM-INSPT005_WORKER_UPDATE.do",
							data: { taskId: vm.id, userKeys: workerKeys },
							dataType: "json"
						})
						.done(function( response ) {
							alert("[" + vm.task.title + "] 담당자 정보가 수정되었습니다.");
						});

					},
					/*증적파일*/
					saveFiles: function () {
						console.log("saveFiles");
						this.$refs.fileManager.saveFiles();
					},
					hasPermission: function () {
						return protectionMeasureTaskTree.hasPermissionOfNode(this.treeTask);
					},
					hasWorkerSectionPermission: function () {

						if (["TASK"].indexOf(this.task.nodeType) === -1)
							return false;

						if (this.mode === "MANAGER")
							return true;

						return this.hasPermission();
					},
					taskPopup: function () {
						openPopUp("/mwork/FM-MWORK021_VIEW_POPUP.do?task=" + this.id)
					},
					statusToText: function (statusValue) {
						var status =_.filter(this.taskStatus, function (element) {
							return element.value == statusValue;
						})
						return status[0].title;
					}

				}
			});
			
			$("#protectionMeasureGuideFileForm").submit(function() {
				var filePath = $("input[name='uploadPath']").val();
				
				if(filePath == "") {
					alert("파일을 선택해주세요.");
					return false;
				} 
			});

		});
		
	</script>

</head>
<body>

<c:import url="/titlebar.do" />

<div class="search">
	    <div class="defalt">
			<form action="/inspt/FM-INSPT005.do" method="get">
	            <ul class="detail">
	        	    <li>

                	    <span class="title" style="line-height: 30px"><label>기간</label></span>

						<select class="selectBox" name="year" style="width: 160px">
						<c:forEach varStatus="status" items="${mainCycleList}" var="cycle">
							<option <c:if test="${selectedYear == cycle.year}">selected="selected"</c:if> value="${cycle.year}">${cycle.name}</option>
						</c:forEach>
						</select>

					</li>
	            </ul>
				<button href="#none" class="btnSearch" style="height: 27px">검색</button>
			</form>
	    </div>
	</div>

<div class="contents">


	<div class="topBtnArea" style="min-height: 0;">
		<c:if test="${mode eq 'MANAGER'}">
		<ul class="btnList">
			<li>
				<button type="button" onclick="openPostPopup('/inspt/FM-INSPT005_RESULT_POPUP.do', 1200, 800, {id : ${protectionMeasure.id}})">
					<span class="icoList"></span>"${protectionMeasure.title}" 현황표
				</button>
			</li>
			<li>
				<button type="button" onclick="openPostPopup('/inspt/FM-INSPT005_REPORT_POPUP.do', 1200, 800, {id : ${protectionMeasure.id}})">
<%--				<button type="button" onclick="alert('구현중 입니다.')">--%>
					<span class="icoDown"></span>보고서 다운로드
				</button>
			</li>
			<li>
				<form action="/inspt/FM-INSPT005_RESULT_EXCEL.do" method="get">
					<input type="hidden" name="id" value="${protectionMeasure.id}">
					<button type="submit"><span class="icoDown"></span>추진과제 다운로드</button>
				</form>
			</li>
		</ul>
		</c:if>
	</div>

	<form method="post" action="/inspt/FM-INSPT005_UPDATE.do" enctype="multipart/form-data" id="protectionMeasureGuideFileForm">
		<input type="hidden" name="id" value="${protectionMeasure.id}">
		<input type="hidden" name="season" value="${selectedYear}">

		<div class="talbeArea mb-5">

			<table class="">

				<tr>
					<th style="width: 200px;">보호지침 파일</th>
					<td class="fontLeft" style="padding-left: 1rem;">
						<a onclick="sendFileRequest(${protectionMeasureGuideFile.umf_fle_key})" href="#">
							<span class="icoDown"></span> ${protectionMeasureGuideFile.umf_con_fnm}
						</a>
						<c:if test="${mode eq 'MANAGER'}">
						<input name="uploadPath" type="file">
						<button type="submit" style="float: right;"><span class="icoSave"></span>보호지침 파일 저장하기</button>
						</c:if>
					</td>
				</tr>

			</table>

		</div>

	</form>

	<div class="border-t mt-5 pt-5">

		<section id="protection-measure-task-tree-section" class="float-left border">

			<div id="protection-measure-menus">
				<ul class="btnList jstree-menus">
					<li v-for="menu in menus">
						<button type="button" v-on:click="menuAction(menu)">{{ menu.label }}</button>
					</li>
				</ul>
			</div>

			<div id="protection-measure-task-tree"></div>

		</section>

		<div class="float-left pl-5">
			<div class="" style="width: 650px;">

				<div id="protection-measure-task">

<%--					<h1 class="font-bold mb-8" style="font-size: 16px;">--%>
<%--						<i class="fa fa-thumb-tack" aria-hidden="true"></i>--%>
<%--						{{ task.title }}--%>
<%--					</h1>--%>

                    <%--중점과제 정보--%>
					<div class="mb-8 card task-section" v-if="task.nodeType == 'TASK'">

						<div class="talbeArea" style="border: 0">
							<table class="mb-3">
								<tr>
									<td style="background-color: #ede9e7; padding: 8px 16px;">
										<span style="float: left; margin-top: 3px; font-size: 14px; font-weight: bold;">중점과제</span>
										<button type="button" v-on:click="taskPopup()" style="float:right;"><span class="icoSynch"></span>중점과제 수정</button>
									</td>
								</tr>
							</table>
						</div>

						<div class="talbeArea">

							<table>
								<colgroup>
									<col width="150px">
									<col width="*">
								</colgroup>
								<tr>
									<th>제목</th>
									<td>{{ task.title }}</td>
								</tr>
								<tr>
									<th>진행상태</th>
									<td>
										{{ statusToText(task.status) }}
									</td>
								</tr>
								<tr>
									<th>내용</th>
									<td><div v-html="task.taskContent"></div></td>
								</tr>
								<tr>
									<th>과제시작일</th>
									<td>{{ task.startedAt | dateFormat }}</td>
								</tr>
								<tr>
									<th>과제종료일</th>
									<td>{{ task.endedAt | dateFormat }}</td>
								</tr>
								<tr>
									<th>목적</th>
									<td>{{ task.purpose }}</td>
								</tr>
								<tr>
									<th>성과평가지표</th>
									<td>{{ task.evaluationIndex }}</td>
								</tr>
								<tr>
									<th>예산</th>
									<td>{{ task.budget }}</td>
								</tr>
								<tr>
									<th>대상시설</th>
									<td>{{ task.targetFacility }}</td>
								</tr>
								<tr>
									<th>증적파일</th>
									<td style="padding-left: 8px">
										<file-list ref="fileManager" tbl-gbn="PTM" con-gbn="12" :con-key="id"></file-list>
									</td>
								</tr>

							</table>
						</div>
					</div>

                    <%--세부업무 정보--%>
					<%--
					<div class="mb-8 card" v-if="task.nodeType == 'MICRO_TASK'">
						<div class="card-header">
							<h1>세부업무 정보</h1>
							<button type="button" v-on:click="saveTask()"><span class="icoSave"></span> 저장하기</button>
						</div>
						<div class="card-body">
							<div class="input-group">
								<div class="input-group__title">내용</div>
								<div>
									<textarea rows="10" class="inputTextarea" v-model="task.taskContent"></textarea>
								</div>
							</div>
						</div>
					</div>
					--%>

                    <%--증적파일 정보--%>
					<%--
					<div class="mb-8 card" v-if="(task.nodeType == 'TASK' && hasPermission()) || task.nodeType == 'MICRO_TASK'">
						<div class="card-header">
							<h1>증적파일</h1>
							<button type="button" v-on:click="saveFiles()"><span class="icoSave"></span> 저장하기</button>
						</div>
						<div class="card-body">
							<file-manager ref="fileManager" tbl-gbn="PTM" con-gbn="12" :con-key="id"></file-manager>
						</div>
					</div>
					--%>

                    <%--담당자 정보--%>

					<div v-if="doShowWorkerSection">


						<div class="talbeArea" style="border: 0">
							<table class="mb-3">
								<tr>
									<td style="background-color: #ede9e7; padding: 8px 16px;">
										<span style="float: left; margin-top: 3px; font-size: 14px; font-weight: bold;">담당자</span>
										<button type="button" v-on:click="saveWorker()" style="float:right;"><span class="icoSave"></span>담당자 저장하기</button>
									</td>
								</tr>
							</table>
						</div>

						<div style="border: 1px solid #ddd; padding: 8px">
							<select-user ref="selectWorker" :origin-users="workerKeys"></select-user>
						</div>

					</div>



</div>

</div>
</div>

</div>


</div>

</body>
</html>