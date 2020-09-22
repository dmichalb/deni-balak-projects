$(document).ready(function(){
    
    var apikey = "df516fe6";

    var url = "http://www.omdbapi.com/?apikey=" + apikey;

    var result = "";
    
    var director = "";
    
    var mpaa = "";
    
    var year = "";
    
    var plot = "";
    
    var stars = "";
    
    var bookmark = "";
    
    $(".container2").hide();

    $("#searchBtn").click(function(event){
        event.preventDefault();
        
        var title = String($("#movie").val());
        $(".movieTitle").val(title);
        
      $.ajax({
        type: 'GET',
        url: url + "&t=" + title,
        success: function(data){
          $("#result").empty();
          
          $(".container2").show();
          
          console.log(data);         

          result = `
          <img class="img-thumbnail" width="200px" height="300px" style="float:left" src="${data.Poster}">
          <h2>${data.Title}</h2>
          <h3>${data.Director}</h3>
          <h3>${data.Year}     ${data.Rated}</h3>
          <p>${data.Plot}<p>
          `;

          $("#result").html(result);

          plot = `${data.Plot}`;
          $(".moviePlot").val(plot);

          director = `${data.Director}`;
          $(".movieDirector").val(director);
          
          mpaa = `${data.Rated}`;
          $(".movieMPAA").val(mpaa);
          
          year = `${data.Year}`;
          $(".movieYear").val(year);

          if(!$("#bookmark").is(":checked")){
              $("#bookmark").val("off");
          }
          console.log($("#bookmark").val());
        },
        error: function(){
            console.log("An error has occurred.");
        }        
      });
    });
    
    $("#bookmark").click(function(){ 
        if(!$("#bookmark").is(":checked")){
              $("#bookmark").val("off");
          } else if ($("#bookmark").is(":checked")){
              $("#bookmark").val("on");
          }
            console.log($("#bookmark").val());
    });
    
    $('#submitBtn').click(function() {
        
               
        
        
        if($('#star5').checked()) {
            $('#star5').val('5');
        } 
        if($('#star4').checked()){
            $('#star4').val('4');
        } 
        if($('#star3').checked()){
            $('#star3').val('3');
        }
        if($('#star2').checked()){
            $('#star2').val('2');
        }
        if($('#star1').checked()){
            $('#star1').val('1');
        }
    });

});