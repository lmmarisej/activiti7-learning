import $ from 'jquery';

import BpmnModeler from 'bpmn-js/lib/Modeler';
//import propertiesPanelModule from '../resources/properties-panel';
import propertiesPanelModule from 'bpmn-js-properties-panel';
import propertiesProviderModule from '../resources/properties-panel/provider/activiti';
import activitiModdleDescriptor from '../resources/activiti.json';
import customTranslate from '../resources/customTranslate/customTranslate';
import customControlsModule from '../resources/customControls';
import tools from '../resources/tools'
import diagramXML from '../resources/newDiagram.bpmn';

const proHost = window.location.protocol + "//" + window.location.host
const href = window.location.href.split("bpmnjs")[0]
const contextPath = href.split(window.location.host)[1]
const publicurl = proHost + contextPath

// 添加翻译组件
var customTranslateModule = {
  translate: ['value', customTranslate]
};
var ColorJson = [{
  'name': 'test1',
  'stroke': 'green',
  'fill': 'yellow'
}, {
  'name': 'test2',
  'stroke': 'blue',
  'fill': 'red'
}]

var container = $('#js-drop-zone');
var canvas = $('#js-canvas');
var bpmnModeler = new BpmnModeler({
  container: canvas,
  propertiesPanel: {
    parent: '#js-properties-panel'
  },
  additionalModules: [
    propertiesPanelModule,
    propertiesProviderModule,
    customControlsModule,
    customTranslateModule
  ],
  moddleExtensions: {
    activiti: activitiModdleDescriptor
  }
});
container.removeClass('with-diagram');
if (!window.FileList || !window.FileReader) {
  window.alert('请使用谷歌、火狐、IE10+浏览器');
} else {
  tools.registerFileDrop(container, tools.createDiagram(diagramXML, bpmnModeler, container));
}


$(function () {
  // 创建 bpmn
  var param = tools.getUrlParam(window.location.href)
  $('.item').show()
  if (param == null || param.type == 'addBpmn') {
    tools.createDiagram(diagramXML, bpmnModeler, container);
  } else if (param.type === 'lookBpmn') { // 编辑 bpmn
    $('.item').hide()
    $('.download').show()
    const Id = param.deploymentFileUUID || '6d4af2dc-bab0-11ea-b584-3cf011eaafca'
    const Name = param.deploymentName || 'String.bpmn'
    // 加载后台方法获取 xml
    var param = {
      "resourceName": Name
    }
    $.ajax({
      url: publicurl + `process/definitions/${Id}/xml`,
      type: 'GET',
      data: param,
      dataType: 'text',
      success: function (result) {
        if (result.success) {
          var newXmlData = result.data
          tools.createDiagram(newXmlData, bpmnModeler, container)
          setTimeout(function () {
            for (var i in ColorJson) {
              tools.setColor(ColorJson[i], bpmnModeler)
            }
          }, 200)
        } else {
          alert(result.message)
        }
      },
      error: function (err) {
        console.log(err)
      }
    });
  }

  // 点击新增
  $('#js-download-diagram').on("click", function () {
    tools.syopen('alert')
  })

  // 点击取消
  $('.cancel').on("click", function () {
    tools.syhide('alert')
  })

  // 点击打开
  $("#openFile").on("change", function () {
    let bpmnFile = this.files[0]
    let fileReader = new FileReader()
    fileReader.readAsText(bpmnFile)
    fileReader.onload = function () {
      console.log(fileReader.result)
      tools.createDiagram(fileReader.result, bpmnModeler, container)
    }
  })

  // 点击上传
  $("#uploadFile").on("change", function () {
    tools.upload(bpmnModeler, container)
  })

  // 点击保存
  $('#sure').on('click', function () {
    const deploymentName = $("#deploymentName").val()
    tools.saveBpmn(bpmnModeler, deploymentName)
  })

  // 点击下载
  $('#saveBpmn').on("click", function () {
    tools.downLoad(bpmnModeler)
  })

});
