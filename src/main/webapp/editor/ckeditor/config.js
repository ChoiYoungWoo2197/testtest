/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	config.language = 'ko';
	config.enterMode = CKEDITOR.ENTER_BR;
	config.toolbarGroups = [

	                		{ name: 'styles', groups: [ 'styles' ] },
	                		{ name: 'colors', groups: [ 'colors' ] },
	                		{ name: 'insert', groups: [ 'insert' ] },
	                		{ name: 'links', groups: [ 'links' ] },
	                		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	                		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] }
	                		];
	config.removeButtons = 'Save,Source,Templates,NewPage,Preview,Print,Cut,Copy,Paste,PasteText,PasteFromWord,Redo,Undo,Find,Replace,SelectAll,Scayt,Form,Radio,Textarea,Select,Button,ImageButton,HiddenField,CreateDiv,Blockquote,BidiLtr,BidiRtl,Language,Anchor,Image,Flash,Smiley,Iframe,PageBreak,Styles,Maximize,ShowBlocks,About';
	config.extraPlugins = 'confighelper';
};