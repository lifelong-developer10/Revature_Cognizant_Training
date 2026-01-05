
  const array=[];
  function arraysFunctions(){
    const number = Number(document.getElementById("num1").value);
    array.push(number);

    document.getElementById("array").innerHTML=array;
    console.log(array);
    

    const sumarray = array.reduce((s1 , i)=>  s1 + i, 0);
    document.getElementById("sum").innerText=sumarray;

    const maxarray = array.reduce((max1,max2) => max1>max2?max1:max2);
    document.getElementById("max").innerText=maxarray;

    document.getElementById("num1").value = "";
  }
 