$(document).ready(function () {
    $("#submit").click(function (e) {
        resetScreen();
        let parameter = prepareParameter();
        let validate = Validate();
        $("#message").html(validate);
        if (validate.length == 0) {
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/api/v1/robot/movement?commands=" + parameter,
                dataType: "json",
                success: function (result) {
                    robotsFinalPosition(result);
                },
                error: function (xhr, status, error) {
                    alert("Result: " + status + " " + error + " " + xhr.status + " " + xhr.statusText)
                }
            });
        }
    });

    function  robotsFinalPosition(result){

        let y = result["position"]["ypos"].toString();
        let x = result["position"]["xpos"].toString();
        let res = y.concat(x);

        let source;

        if("NORTH" === result["direction"]){
            source = "/images/north.png"
        }else if("WEST" === result["direction"]){
            source = "/images/west.png"
        }else if("SOUTH" === result["direction"]){
            source = "/images/south.png"
        }else if("EAST" === result["direction"]){
            source = "/images/east.png"
        }

        document.getElementById(res).src=source;
    }

    function prepareParameter(commands){
        let formattedtemp = "";
        splitted = $("#txtCommands").val().split("\n");

        return splitted.join();
    }

    function resetScreen(){
        for (let i = 0; i < 5; i++) {
            for (let j = 0; j < 5; j++) {
                let position = i.toString().concat(j.toString());
                document.getElementById(position).src="/images/white.png";
            }
        }
    }

    function Validate() {
        let errorMessage = "";
        if ($("#txtCommands").val().length == 0) {
            errorMessage += "You have to enter commands!";
        }
        return errorMessage;
    }
});