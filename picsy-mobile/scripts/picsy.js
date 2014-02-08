var _picsFile = "picsy.dat";
var _url = "http://{yourappurl}/picsy";
function PicsyPic(){
	this.photo = null;
	this.desc = null;
	this.by = null;
}
$m.juci.addDataset("pics", []);
$m.onReady(function(){
	// Code to execute when the page is ready
	// readFromFile();
	readFromCloud();
});

function addPic(){
	$m.juci.getControl("picsy-list").createItem(new PicsyPic());
}

function openPhoto(e){
	e.preventDefault();
	if(!$m.isWeb()){
		$m.showAsGallery({
			"title": "Picsy",
			"images": [{"url": e.data.photo, "title": e.data.desc}],
			"buttonLabel": "Done",
			"index": 0
		});
	}
}

function readFromCloud(){
	$m.juci.dataset("pics", []);
	$m.showProgress("Refreshing...");
	$m.get(_url, function(response){
		$m.hideProgress();
		if(response.code === 200){
			// Success
			var result = response.result;
			var pics = JSON.parse(result.data);
			$m.juci.dataset("pics", pics, true);
		} else{
			// Error
			var errMsg = response.error.message;
		}
	});
}

function uploadToCloud(e){
	$m.showProgress("Saving...");
	e.preventDefault();
	var pic = e.newData;
	// TODO Change localhost:8888 to actual address of the server
	// after deploy
	var req = $m.httpRequest({"type":"POST",
		"url":_url});
	var newFile = $m.file(pic.photo, {level: $m.STORAGE_LEVEL});
	req.addPart("photo", newFile);
	req.addPart("desc", pic.desc);
	req.addPart("by", pic.by);
	req.send(function(r){
		// Callback to execute on receiving response
		$m.hideProgress();
		if(r.code == 200){
			$m.logInfo("Upload done");
			e.control.addItem(JSON.parse(r.result.data), true);
			$m.juci.closePanel();
			// TODO Try refreshing instead of add
		}else{
			// TODO Alert error
			$m.logError("Upload failed - " + r.error.message);
		}
	});
}

function readFromFile(){
	$m.readFile(_picsFile, function(response){
		if(response.code == -1) {
			// External storage is not ready. Possible reason could be the storage
			// is mounted to file system on a computer.
			return;
		}
		if(response.code){
			// Success
			var fileContent = response.result;
			$m.juci.dataset("pics", fileContent, true);
		} else{
			// Error
			var errMsg = response.error.message;
		}
	});
}

function writeToFile(){
	$m.writeFile(_picsFile, $m.juci.dataset("pics"), function(response){
		if(response.code == -1) {
			// External storage is not ready. Possible reason could be the storage
			// is mounted to file system on a computer.
			return;
		}
		if(response.code){
			// Success
			$m.logInfo("File write done");
			$m.toast("Dont worry, all taken care!");
		} else{
			// Error
			var errMsg = response.error.message;
			$m.logError("File write fail - " + errMsg);
		}
	});
}