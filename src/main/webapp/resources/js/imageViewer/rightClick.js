$.contextMenu({
		selector : '#imageDrawing',
		callback : function(key, options) {
			switch (key) {
			case "fold1-key1":
				$("#zoomFullPage").trigger("click");
				break;
			case "fold1-key2":
				$("#zoomFullWidth").trigger("click");
				break;
			case "fold1-key3":
				$("#zoom200").trigger("click");
				break;
			case "fold1-key4":
				$("#zoom100").trigger("click");
				break;
			case "fold1-key5":
				$("#zoom75").trigger("click");
				break;
			case "fold1-key6":
				$("#zoom50").trigger("click");
				break;
			case "Layers...":
				$("#btnLayer").trigger("click");
				break;
			}
		},
		items : {
//			radio1: {
//                name: "Select", 
//                type: 'radio', 
//                radio: 'radio', 
//                value: '1'
//            },
//            radio2: {
//                name: "Zoom", 
//                type: 'radio', 
//                radio: 'radio', 
//                value: '2', 
//                selected: true
//            },
//            sep2: "---------",
            "fold1": {
                "name": "ズーム",
                "items": {
                    "fold1-key1": {"name": "全表示"},
                    "fold1-key2": {"name": "幅にわせる"},
                    "fold1-key3": {"name": "200%"},
                    "fold1-key4": {"name": "100%"},
                    "fold1-key5": {"name": "75%"},
                    "fold1-key6": {"name": "50%"}
                }
            },
			"Layers..." : {
				name : "レイヤー...",
				icon : "edit",
				accesskey : "p"
			}
		}
	});	