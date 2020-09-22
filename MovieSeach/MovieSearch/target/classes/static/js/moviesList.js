$(document).ready(function(){
   $("#seeAll").hide();
   
   
   
   $("#bookmarkedOnly").click(function(event){
    event.preventDefault();

    if($("#bookmarkedCell").val() === false){
        var row = this.rowIndex;
        
        row.hide();
    }

    $("#seeAll").show();
   });
   
   $("#seeAll").click(function(event){
       event.preventDefault();
       
       if($("#bookmarkedCell").val() === 'false'){
        var row = this.rowIndex;
        
        row.show();
    }

    $("#seeAll").hide();
       
   });
});
