				// JavaScript library ，用于校验表单数据
// 杨斌  整理修改于 2003.6.7
// 简要目录：
// 1.是否为空或全是空字符
// 2.字符串是否只包含0到9的数字
// 3.是否整数(允许带正负号)
// 4.是否数字
// 5.小数（m,n），忽略小数首尾的0，不允许正负号
// 6.字符串是否全是字母
// 7.字符串是否全是字母和数字
// 8.日期
// 9.居民身份证号码
// 10.电话号码
// 11.邮政编码
// 12.是否合法的email
// 13.一列checkbox中是否至少有一个checked 14.一列checkbox中是否只有一个checked
// 15.一列checkbox中是否全部checked 16.多个下拉框不能一样
// 16.checkdate,校验日期是否合法
// 17：格式化数字
// 其它未编号的function为辅助，也可以单独使用

// 注意：除固定格式的输入项如日期，邮政编码和居民身份证号码外，其余均未判断长度和是否为空。

var digits = "0123456789";
var lowercaseLetters = "abcdefghijklmnopqrstuvwxyz"
var uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
var whitespace = " \t\n\r";
var defaultEmptyOK = true


//是否 null 或者 空
function isEmpty(s)
{
   return ((s == null) || (s.length == 0) )
}

//1.是否为空或全是空字符
function isWhitespace (s)

{   var i;

    if (isEmpty(s)) return true;

    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);

        if (whitespace.indexOf(c) == -1) return false;
    }

    return true;
}

//从s里去掉包含在bag中的字符，s="abcd" bag="ace" return "bd"
function stripCharsInBag (s, bag)

{   var i;
    var returnString = "";

    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }

    return returnString;
}
//从s里去掉不包含在bag中的字符 s="abcd" bag="ace" return "ac"
function stripCharsNotInBag (s, bag)

{   var i;
    var returnString = "";

    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);
        if (bag.indexOf(c) != -1) returnString += c;
    }

    return returnString;
}
//从s里去掉空字符，s="ab c d " return "abcd"
function stripWhitespace (s)

{   return stripCharsInBag (s, whitespace)
}

//字符c是否在字符串s中
function charInString (c, s)
{   for (i = 0; i < s.length; i++)
    {   if (s.charAt(i) == c) return true;
    }
    return false
}

//去掉字符串前面的空字符
function stripInitialWhitespace (s)

{   var i = 0;

    while ((i < s.length) && charInString (s.charAt(i), whitespace))
       i++;

    return s.substring (i, s.length);
}
//字符是否为字母
function isLetter (c)
{   return ( ((c >= "a") && (c <= "z")) || ((c >= "A") && (c <= "Z")) )
}

//字符是否为0-9
function isDigit (c)
{   return ((c >= "0") && (c <= "9"))
}

//字符是否为字母或数字
function isLetterOrDigit (c)
{   return (isLetter(c) || isDigit(c))
}
//字符是否为数字或"-"
function isTel(c)
{   return (((c >= "0") && (c <= "9"))|| (c=="-"))
}

//2.字符串是否只包含0到9的数字
function isInteger (s)

{   var i;

    if (isEmpty(s))
       if (isInteger.arguments.length == 1) return defaultEmptyOK;
       else return (isInteger.arguments[1] == true);


    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);

        if (!isDigit(c)) return false;
    }

    return true;
}


//3.是否整数(允许带正负号)
function isSignedInteger (s)

{   if (isEmpty(s))
       if (isSignedInteger.arguments.length == 1) return defaultEmptyOK;
       else return (isSignedInteger.arguments[1] == true);

    else {
        var startPos = 0;
        var secondArg = defaultEmptyOK;

        if (isSignedInteger.arguments.length > 1)
            secondArg = isSignedInteger.arguments[1];

        if ( (s.charAt(0) == "-") || (s.charAt(0) == "+") )
           startPos = 1;
        return (isInteger(s.substring(startPos, s.length), secondArg))
    }
}

//是否为大于零的整数（允许正负号）
function isPositiveInteger (s)
{   var secondArg = defaultEmptyOK;

    if (isPositiveInteger.arguments.length > 1)
        secondArg = isPositiveInteger.arguments[1];

    return (isSignedInteger(s, secondArg)&& ( (isEmpty(s) && secondArg)  || (parseInt (s,10) > 0) ) );
}

//是否为大于等于零的整数（允许正负号）
function isNonnegativeInteger (s)
{   var secondArg = defaultEmptyOK;

    if (isNonnegativeInteger.arguments.length > 1)
        secondArg = isNonnegativeInteger.arguments[1];

    return (isSignedInteger(s, secondArg)
         && ( (isEmpty(s) && secondArg)  || (parseInt (s,10) >= 0) ) );
}

//是否为小于零的整数（允许正负号）
function isNegativeInteger (s)
{   var secondArg = defaultEmptyOK;

    if (isNegativeInteger.arguments.length > 1)
        secondArg = isNegativeInteger.arguments[1];

    return (isSignedInteger(s, secondArg)
         && ( (isEmpty(s) && secondArg)  || (parseInt (s,10) < 0) ) );
}

//是否为小于等于零的整数（允许正负号）
function isNonpositiveInteger (s)
{   var secondArg = defaultEmptyOK;

    if (isNonpositiveInteger.arguments.length > 1)
        secondArg = isNonpositiveInteger.arguments[1];

    return (isSignedInteger(s, secondArg)
         && ( (isEmpty(s) && secondArg)  || (parseInt (s,10) <= 0) ) );
}


//4.是否数字
function isFloat (s)
{
    var i;
    var seenDecimalPoint = false;

    if (isWhitespace(s))
    	return false;
    if (isEmpty(s))
       if (isFloat.arguments.length == 1) return defaultEmptyOK;
       else return (isFloat.arguments[1] == true);
    if (s == ".") return false;
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if ((c == ".") && !seenDecimalPoint) seenDecimalPoint = true;
        else if (!isDigit(c)) return false;
    }
    return true;
}

//是否数字（允许正负号）
function isSignedFloat (s)

{   if (isEmpty(s))
       if (isSignedFloat.arguments.length == 1) return defaultEmptyOK;
       else return (isSignedFloat.arguments[1] == true);

    else {
        var startPos = 0;
        var secondArg = defaultEmptyOK;

        if (isSignedFloat.arguments.length > 1)
            secondArg = isSignedFloat.arguments[1];

        // skip leading + or -
        if ( (s.charAt(0) == "-") || (s.charAt(0) == "+") )
           startPos = 1;
        return (isFloat(s.substring(startPos, s.length), secondArg))
    }
}

//5.小数（m,n），忽略小数首尾的0，不允许正负号
function isDecimal(s,m,n){
	if(!isFloat(s)) return false;
	if(String(parseInt(s,10)).length > m-n) return false;
	var ss = String(parseFloat(s));
	if(ss.indexOf(".")>=0 && ss.substring( ss.indexOf(".") + 1, ss.length).length > n ) return false;
	return true;
}


//6.字符串是否全是字母
function isAlphabetic (s)
{   var i;

    if (isEmpty(s))
       if (isAlphabetic.arguments.length == 1) return defaultEmptyOK;
       else return (isAlphabetic.arguments[1] == true);
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is letter.
        var c = s.charAt(i);
        if (!isLetter(c))
        return false;
    }
    // All characters are letters.
    return true;
}
//7.字符串是否全是字母和数字
function isAlphanumeric (s)

{   var i;

    if (isEmpty(s))
       if (isAlphanumeric.arguments.length == 1) return defaultEmptyOK;
       else return (isAlphanumeric.arguments[1] == true);

    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);

        if (! (isLetter(c) || isDigit(c) ) )
        return false;
    }

    return true;
}


//8.日期，format 可设定为 "yyyy-MM-dd"（注意大小写），分隔符可变
function toValidateDate(str,format) {
	if(str.length != format.length) return false;

	var year = 2000;
	var month = 1;
	var day = 1;
	var hour = 0;
	var minute = 0;
	var second = 0;

	if(format.indexOf("yyyy") != -1) {
		if(isNaNEx(year = SearchEx(str,format,"yyyy"))) return false;
		format = format.replace(/yyyy/,year);
	}

	if(format.indexOf("MM") != -1) {
		if(isNaNEx(month = SearchEx(str,format,"MM"))) return false;
		format = format.replace(/MM/,month);
	}

	if(format.indexOf("dd") != -1) {
		if(isNaNEx(day = SearchEx(str,format,"dd"))) return false;
		format = format.replace(/dd/,day);
	}

	if(format.indexOf("HH") != -1) {
		if(isNaNEx(hour = SearchEx(str,format,"HH"))) return false;
		if(parseInt(hour,10) < 0 || parseInt(hour,10) > 23) return false;
		format = format.replace(/HH/,hour);
	}

	if(format.indexOf("mm") != -1) {
		if(isNaNEx(minute = SearchEx(str,format,"mm"))) return false;
		if(parseInt(minute,10) < 0 || parseInt(minute,10) > 59) return false;
		format = format.replace(/mm/,minute);
	}

	if(format.indexOf("ss") != -1) {
		if(isNaNEx(second = SearchEx(str,format,"ss"))) return false;
		if(parseInt(second,10) < 0 || parseInt(second,10) > 59) return false;
		format = format.replace(/ss/,second);
	}

	if(format != str) return false;

	return isValidDate(year,month,day);
}
//日期
function isNaNEx(str) {
	if(str == "") return true;
	if(isNaN(str)) return true;
	if(str.indexOf(".") != -1) return true;
	return false;
}
//日期
function SearchEx(source,pattern,str) {
	var index = pattern.indexOf(str);
	if(index == -1) return "error";
	return source.substring(index,index + str.length);
}
//日期
function isValidDate(year,month,day) {
	month = parseInt(month,10);
	day = parseInt(day,10);

	if(month < 1 || month > 12) return false;
	if(day < 1 || day > 31) return false;
	if((month == 4 || month == 6 || month == 9 || month == 11) && (day == 31)) return false;
	if (month == 2) {
		var leap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day == 29 && !leap)) return false;
	}
	return true;
}


//9.居民身份证号码(15或18位，最后一位数字或字母，其余数字，生日合法)
function isResidentID (s){
	if(s.length!=15 && s.length!=18) return false;
	if(!isAlphanumeric(s)) return false;
	var birthday;
	if(s.length==18){
		if( !isInteger(s.substring(0,s.length-1)) ) return false;
		birthday = s.substring(6,14);
	}
	if(s.length==15){
		if( !isInteger(s) ) return false;
		birthday = "19"+s.substring(6,12);
	}
	if(!isValidDate(  birthday.substring(0,4), birthday.substring(4,6), birthday.substring(6,8)  )) return false;
	return true;

}

//字符串按格式重组(reformat("20021213","",4,"-",2,"-",2),会返回2002-12-13)
function reformat (s)

{   var arg;
    var sPos = 0;
    var resultString = "";

    for (var i = 1; i < reformat.arguments.length; i++) {
       arg = reformat.arguments[i];
       if (i % 2 == 1) resultString += arg;
       else {
           resultString += s.substring(sPos, sPos + arg);
           sPos += arg;
       }
    }
    return resultString;
}

//10.电话号码( 数字和+,-,() )
function isPhoneNumber(s){
	if(stripCharsInBag(s,"0123456789-()+")!="") return false;
	return true;
}

//11.邮政编码
function isPostCode(s){
	return(isInteger(s) && s.length==6);
}

//是否只包含合法的email字符
function isvalidEmailChar (s)
{   var i;

    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);

        if (! (isLetter(c) || isDigit(c) || (c=='@') || (c=='.') || (c=='_') || (c=='-') || (c=='+')) ) {
       	return false;
		}
    }

    return true;
}

//12.是否合法的email
function isEmail (s)
{   if (isEmpty(s))
       if (isEmail.arguments.length == 1) return defaultEmptyOK;
       else return (isEmail.arguments[1] == true);

    if (isWhitespace(s)) return false;
    if (!isvalidEmailChar(s)) return false;

    atOffset = s.lastIndexOf('@');

    if ( atOffset < 1 )
        return false;
    else {
 	dotOffset = s.indexOf('.', atOffset);

      if ( dotOffset < atOffset + 2 ||
         dotOffset > s.length - 2 ) {
         return false;
      }
   }
   return true;
}



//13.一列checkbox中是否至少有一个checked，输入checkbox对象，输出 t or f
function isChecked(checkbox_name)
  {
      var items=checkbox_name.length;
      if(items>1){
         for(i=0;i<items;i++)
           {
           if(checkbox_name[i].checked==true) return true;
           }
         }
      else
         if(checkbox_name.checked==true) return true;
      return false;
  }

//14.一列checkbox中是否只有一个checked，输入checkbox对象，输出 t or f
function isCheckedOne(checkbox_name)
{
      var items=checkbox_name.length;
      var count=0;
      if(items>1){
         for(i=0;i<items;i++)
           {
           if(checkbox_name[i].checked==true) count++;
           }
         if(count==1) return true;
         else
      	    return false;
       }
      else
        {
         if(checkbox_name.checked==true) return true;
         else
      	    return false;
        }
}




//16.多个下拉框不能一样
function selectUnique(theForm)
{
  for (i=0; i<theForm.elements.length; i++)
  {
    e = theForm.elements[i];
    if ( e.type=="select-one" && !e.isDisabled)
    {
      for(j=0; j<theForm.elements.length; j++)
      {
        e2 = theForm.elements[j];
        if ( e2.type=="select-one" && !e2.isDisabled)
        {
          if(e!=e2 && e.options[e.selectedIndex].value==e2.options[e2.selectedIndex].value)
          {
            e.focus();
            return false;
          }
        }
      }
    }
  }
  return true;
}

//////信用卡号码（注意，原程序为美国使用，未修改）

function isCreditCard(st) {
  // Encoding only works on cards with less than 19 digits
  if (st.length > 19)
    return (false);

  sum = 0; mul = 1; l = st.length;
  for (i = 0; i < l; i++) {
    digit = st.substring(l-i-1,l-i);
    tproduct = parseInt(digit ,10)*mul;
    if (tproduct >= 10)
      sum += (tproduct % 10) + 1;
    else
      sum += tproduct;
    if (mul == 1)
      mul++;
    else
      mul--;
  }

  if ((sum % 10) == 0)
    return (true);
  else
    return (false);

}


function isVisa(cc)
{
  if (((cc.length == 16) || (cc.length == 13)) &&
      (cc.substring(0,1) == 4))
    return isCreditCard(cc);
  return false;
}


function isMasterCard(cc)
{
  firstdig = cc.substring(0,1);
  seconddig = cc.substring(1,2);
  if ((cc.length == 16) && (firstdig == 5) &&
      ((seconddig >= 1) && (seconddig <= 5)))
    return isCreditCard(cc);
  return false;

}

function isAmericanExpress(cc)
{
  firstdig = cc.substring(0,1);
  seconddig = cc.substring(1,2);
  if ((cc.length == 15) && (firstdig == 3) &&
      ((seconddig == 4) || (seconddig == 7)))
    return isCreditCard(cc);
  return false;

}

function isDinersClub(cc)
{
  firstdig = cc.substring(0,1);
  seconddig = cc.substring(1,2);
  if ((cc.length == 14) && (firstdig == 3) &&
      ((seconddig == 0) || (seconddig == 6) || (seconddig == 8)))
    return isCreditCard(cc);
  return false;
}

function isCarteBlanche(cc)
{
  return isDinersClub(cc);
}


function isDiscover(cc)
{
  first4digs = cc.substring(0,4);
  if ((cc.length == 16) && (first4digs == "6011"))
    return isCreditCard(cc);
  return false;

}


function isEnRoute(cc)
{
  first4digs = cc.substring(0,4);
  if ((cc.length == 15) &&
      ((first4digs == "2014") ||
       (first4digs == "2149")))
    return isCreditCard(cc);
  return false;
}

function isJCB(cc)
{
  first4digs = cc.substring(0,4);
  if ((cc.length == 16) &&
      ((first4digs == "3088") ||
       (first4digs == "3096") ||
       (first4digs == "3112") ||
       (first4digs == "3158") ||
       (first4digs == "3337") ||
       (first4digs == "3528")))
    return isCreditCard(cc);
  return false;

}

function isAnyCard(cc)
{
  if (!isCreditCard(cc))
    return false;
  if (!isMasterCard(cc) && !isVisa(cc) && !isAmericanExpress(cc) && !isDinersClub(cc) &&
      !isDiscover(cc) && !isEnRoute(cc) && !isJCB(cc)) {
    return false;
  }
  return true;

}

function isCardMatch (cardType, cardNumber)
{

	cardType = cardType.toUpperCase();
	var doesMatch = true;

	if ((cardType == "VISA") && (!isVisa(cardNumber)))
		doesMatch = false;
	if ((cardType == "MASTERCARD") && (!isMasterCard(cardNumber)))
		doesMatch = false;
	if ( ( (cardType == "AMERICANEXPRESS") || (cardType == "AMEX") )
                && (!isAmericanExpress(cardNumber))) doesMatch = false;
	if ((cardType == "DISCOVER") && (!isDiscover(cardNumber)))
		doesMatch = false;
	if ((cardType == "JCB") && (!isJCB(cardNumber)))
		doesMatch = false;
	if ((cardType == "DINERS") && (!isDinersClub(cardNumber)))
		doesMatch = false;
	if ((cardType == "CARTEBLANCHE") && (!isCarteBlanche(cardNumber)))
		doesMatch = false;
	if ((cardType == "ENROUTE") && (!isEnRoute(cardNumber)))
		doesMatch = false;
	return doesMatch;

}

function IsCC (st) {
    return isCreditCard(st);
}

function IsVisa (cc)  {
  return isVisa(cc);
}

function IsVISA (cc)  {
  return isVisa(cc);
}

function IsMasterCard (cc)  {
  return isMasterCard(cc);
}

function IsMastercard (cc)  {
  return isMasterCard(cc);
}

function IsMC (cc)  {
  return isMasterCard(cc);
}

function IsAmericanExpress (cc)  {
  return isAmericanExpress(cc);
}

function IsAmEx (cc)  {
  return isAmericanExpress(cc);
}

function IsDinersClub (cc)  {
  return isDinersClub(cc);
}

function IsDC (cc)  {
  return isDinersClub(cc);
}

function IsDiners (cc)  {
  return isDinersClub(cc);
}

function IsCarteBlanche (cc)  {
  return isCarteBlanche(cc);
}

function IsCB (cc)  {
  return isCarteBlanche(cc);
}

function IsDiscover (cc)  {
  return isDiscover(cc);
}

function IsEnRoute (cc)  {
  return isEnRoute(cc);
}

function IsenRoute (cc)  {
  return isEnRoute(cc);
}

function IsJCB (cc)  {
  return isJCB(cc);
}

function IsAnyCard(cc)  {
  return isAnyCard(cc);
}

function IsCardMatch (cardType, cardNumber)  {
  return isCardMatch (cardType, cardNumber);
}
//////////////////


function checkdate(strDate,minValue,maxValue)
{
  var ii=0;
  var aa=new Array();
  var value1=strDate.value;
//取最小值
  var m_minValue=String(minValue);
  if (minValue=='')
    m_minValue = '1900-01-01';
  var fi=m_minValue.indexOf('-');
  var li=m_minValue.lastIndexOf('-');
  var min_Year=''+m_minValue.substring(0,fi);//取出年
  var min_Month=m_minValue.substring(fi+1,li);//取出月
  if(min_Month.length==1)
  {
    min_Month='0'+min_Month;
  }
  var min_Date=m_minValue.substring(li+1,m_minValue.length);//取出日
  if(min_Date.length==1)
  {
    min_Date='0'+min_Date;
  }
//取最大值
  var m_maxValue=String(maxValue);
  if (maxValue=='')
    m_maxValue = '9999-12-31';
  var fa=m_maxValue.indexOf('-');
  var la=m_maxValue.lastIndexOf('-');
  var max_Year=''+m_maxValue.substring(0,fa);//取出年
  var max_Month=m_maxValue.substring(fa+1,la);//取出月
  if(max_Month.length==1)
  {
    max_Month='0'+max_Month;
  }
  var max_Date=m_maxValue.substring(la+1,m_maxValue.length);//取出日
  if(max_Date.length==1)
  {
    max_Date='0'+max_Date;
  }

  i_flag=false;
  if(value1!='')
  {
    if(value1.length>10||value1.length<8)
    {
      alert('输入格式应为 YYYY-MM-DD.');
    }
    else
    {
      aa[0]=-1;
      for(var j=0;j<value1.length;j++)
      {
        var k=value1.indexOf('-',aa[ii]+1);
        if(k!=-1)
        {
          ii=ii+1;
          aa[ii]=k;
        }
      }
      if(ii>2)
      {
        alert('输入格式应为 YYYY-MM-DD.');
      }
      else
      {
        if(value1.indexOf('-')!=4)
        {
          alert('输入格式应为YYYY-MM-DD.');
        }
        else
        {
          if((value1.indexOf('-',5)!=6)&&(value1.indexOf('-',5)!=7))
          {
            alert('输入格式应为 YYYY-MM-DD.');
          }
          else//开始日期校验
          {
            var f_i=value1.indexOf('-');
            var l_i=value1.lastIndexOf('-');
            var iYear=''+value1.substring(0,f_i);//取出年
            var iMonth=value1.substring(f_i+1,l_i);//取出月
            if(iMonth.length==1)
            {
              iMonth='0'+iMonth;
            }
            var iDate=value1.substring(l_i+1,value1.length);//取出日
            if(iDate.length==1)
            {
              iDate='0'+iDate;
            }
            if(parseFloat(iMonth)<1||parseFloat(iMonth)>12)
            {
              alert('请输入1-12之间的月份.');
            }
            else
            {
              var k_Y=parseFloat(''+iYear);
              var k_M=parseFloat(''+iMonth);
              var k_D=parseFloat(''+iDate);
              if(k_M==1||k_M==3||k_M==5||k_M==7||k_M==8||k_M==10||k_M==12)
              {
                if(k_D<1||k_D>31)
                {
                  alert('请输入 1-31 之间的日期');
                }
                else
                {
                  if(parseFloat(iYear+iMonth+iDate)<parseFloat(min_Year+min_Month+min_Date))
                  {
                    alert('输入不能小于'+m_minValue);
                  }
                  else
                  {
                    if(parseFloat(iYear+iMonth+iDate)>parseFloat(max_Year+max_Month+max_Date))
                    {
                      alert('输入不能大于'+m_maxValue);
                    }
                    else
                    {
                      i_flag=true;
                    }
                  }
                }
              }
              if (k_M==2)
              {
                if (k_Y%400==0||(k_Y%4==0&&k_Y%100!=0))
                {
                  if(k_D<1||k_D>29)
                  {
                    alert('请输入 1-29 之间的日期.');
                  }
                  else
                  {
                    if(parseFloat(iYear+iMonth+iDate)<parseFloat(min_Year+min_Month+min_Date))
                    {
                      alert('输入不能小于'+m_minValue);
                    }
                    else
                    {
                      if(parseFloat(iYear+iMonth+iDate)>parseFloat(max_Year+max_Month+max_Date))
                      {
                        alert('输入不能大于'+m_maxValue);
                      }
                      else
                      {
                        i_flag=true;
                      }
                    }
                  }
                }
                else
                {
                  if(k_D<1||k_D>28)
                  {
                    alert('请输入 1-28 之间的日期.');
                  }
                  else
                  {
                    if(parseFloat(iYear+iMonth+iDate)<parseFloat(min_Year+min_Month+min_Date))
                    {
                      alert('输入不能小于'+m_minValue);
                    }
                    else
                    {
                      if(parseFloat(iYear+iMonth+iDate)>parseFloat(max_Year+max_Month+max_Date))
                      {
                        alert('输入不能大于'+m_maxValue);
                      }
                      else
                      {
                        i_flag=true;
                      }
                    }
                  }
                }
              }
              if(k_M==4||k_M==6||k_M==9||k_M==11)
              {
                if(k_D<1||k_D>30)
                {
                  alert('请输入 1-30 之间的日期.');
                }
                else
                {
                  if(parseFloat(iYear+iMonth+iDate)<parseFloat(min_Year+min_Month+min_Date))
                  {
                    alert('输入不能小于'+m_minValue);
                  }
                  else
                  {
                    if(parseFloat(iYear+iMonth+iDate)>parseFloat(max_Year+max_Month+max_Date))
                    {
                      alert('输入不能大于'+m_maxValue);
                    }
                    else
                    {
                      i_flag=true;
                    }
                  }
	              }
	            }
	          }
	        }
	      }
	    }
   	}
  }//end if
  else
    i_flag = true;
  start_date_valid = i_flag;
  if (!i_flag)
  {
    //strDate.value = '';
    strDate.focus();
    strDate.select();
  }
  return i_flag;
}//end function


function numberformat (expr, decplaces) {
	 var str = '' + Math.round (eval(expr) * Math.pow(10,decplaces))
	 while (str.length <= decplaces) {
		str = '0' + str
	 }
	 var decpoint = str.length - decplaces
	 return str.substring(0,decpoint) + '.' + str.substring(decpoint,str.length);
}

 /**数量筐失去焦点时触发该事件
 	参数说明：
 	strQuantity：数量筐名称，默认调用this
 	strValue:与数量筐相乘的数量字符串，一般都是单价
 	strCountThis：统计一行金额的DIV名称
 	strCountQuantity：统一总数量DIV名称
 	strCountTotal：统一总量DIV名称
 **/
 function quantitychange(strQuantity,strValue,strCountThis,strCountQuantity,strCountTotal)
 {
	var Elm = event.srcElement;
	while(Elm && Elm.tagName != "TR")
	{
		Elm = Elm.parentElement;
	}
	iRowIndex=Elm.rowIndex;
	iRowIndex=parseInt(iRowIndex);


	if (isFloat(eval("document.forms[0]."+strQuantity+"["+iRowIndex+"].value"))){
	  	   var sCount=eval("document.forms[0]."+strQuantity+"["+iRowIndex+"].value")*eval("document.forms[0]."+strValue+"["+iRowIndex+"].value");
	  	   eval("document.forms[0]."+strCountThis+"["+iRowIndex+"].value="+sCount);
	  	   eval("document.forms[0]."+strCountThis+"["+iRowIndex+"].value=numberformat\(document.forms[0]."+strCountThis+"["+iRowIndex+"].value,2\)");


		   var moneytmp = 0;
		   var quantitytmp=0;
		   var strQuan=0;
		   var iLength=eval("document.forms[0]."+strCountThis+".length");

		   for (var j=1;j<iLength;j++)
		   {
		   	strTotal= eval("document.forms[0]."+strCountThis+"["+j+"].value");
		   	if (isEmpty(eval(strTotal)))
				strTotal=0;
	    		moneytmp += eval(strTotal);

	    		strQuan= eval("document.forms[0]."+strQuantity+"["+j+"].value");
			if (isEmpty(eval(strQuan)))
				strQuan=0;
	    		quantitytmp += eval(strQuan);

		   }
		   eval("document.forms[0]."+strCountTotal+".value="+moneytmp);
		   eval("document.forms[0]."+strCountTotal+".value=numberformat\(document.forms[0]."+strCountTotal+".value,2\)");
		   eval("document.forms[0]."+strCountQuantity+".value="+quantitytmp);
		   eval("document.forms[0]."+strCountQuantity+".value=numberformat\(document.forms[0]."+strCountQuantity+".value,2\)");

	}
	else{
		alert("请输入数字并且不能为空!");
		eval("document.forms[0]."+strQuantity+"["+iRowIndex+"].select()");
		return false;
	}
 }

  /**系统调用该页面时触发该事件
 	参数说明：
 	strQuantity：数量筐名称，默认调用this
 	strValue:与数量筐相乘的数量字符串，一般都是单价
 	strCountThis：统计一行金额的DIV名称
 	strCountQuantity：统一总数量DIV名称
 	strCountTotal：统一总量DIV名称
 **/

 function initData(strQuantity,strValue,strCountThis,strCountQuantity,strCountTotal){
 	var iLength=eval("document.forms[0]."+strQuantity+".length");
        var moneytmp = 0;
  	var quantitytmp=0;
   	var strQuan=0;
   	var sCount=0;

 	for(j=1;j<iLength;j++){
 		sCount=eval("document.forms[0]."+strQuantity+"["+j+"].value")*eval("document.forms[0]."+strValue+"["+j+"].value");
     	        eval("document.forms[0]."+strCountThis+"["+j+"].value="+sCount);
	  	eval("document.forms[0]."+strCountThis+"["+j+"].value=numberformat\(document.forms[0]."+strCountThis+"["+j+"].value,2\)");

	   	strTotal= eval("document.forms[0]."+strCountThis+"["+j+"].value");
	   	if (isEmpty(eval(strTotal)))
			strTotal=0;
    		moneytmp += eval(strTotal);

    		strQuan= eval("document.forms[0]."+strQuantity+"["+j+"].value");
		if (isEmpty(eval(strQuan)))
			strQuan=0;
    		quantitytmp += eval(strQuan);

	   }
	   eval("document.forms[0]."+strCountTotal+".value="+moneytmp);
	   eval("document.forms[0]."+strCountTotal+".value=numberformat\(document.forms[0]."+strCountTotal+".value,2\)");
	   eval("document.forms[0]."+strCountQuantity+".value="+quantitytmp);
	   eval("document.forms[0]."+strCountQuantity+".value=numberformat\(document.forms[0]."+strCountQuantity+".value,2\)");
}

function checkAll(checkall,boxName){
	iLength=eval("document.forms[0]."+boxName+".length");
	if (isEmpty(eval(iLength))){
		iFlag=eval("document.forms[0]."+checkall+".checked")
		if (iFlag)
			eval("document.forms[0]."+boxName+".checked=true");
		else
			eval("document.forms[0]."+boxName+".checked=false");
	}
	else{
		iFlag=eval("document.forms[0]."+checkall+".checked")
		for(i=0;i<iLength;i++){
			if (iFlag)
				eval("document.forms[0]."+boxName+"["+i+"].checked=true");
			else
				eval("document.forms[0]."+boxName+"["+i+"].checked=false");
		}

	}


}

function checkThis(strValue,strCheckName){
	var strLength=eval("document.forms[0]."+strCheckName+".length");
	if ((strLength == null) || (strLength.length == 0)){
		eval("document.forms[0]."+strCheckName+".checked=true");
	}
	else{
		var Elm = event.srcElement;
	while(Elm && Elm.tagName != "TR")
	{
		Elm = Elm.parentElement;
	}
	iRowIndex=Elm.rowIndex;
	iRowIndex=parseInt(iRowIndex)-1;
	for(var i=0;i<strLength;i++){
		eval("document.forms[0]."+strCheckName+"["+i+"].checked=false;");
	}
		eval("document.forms[0]."+strCheckName+"["+iRowIndex+"].checked=true");
	}
       // parent.down.location.href='OrderApplyOperate.jsp?ORDER_ID='+strValue;
    }
function checkJumpPage() {
var form = document.forms[0];
// 用户输入的页码
var strJumpPage = form.jumpPage.value;
// 总页数
var strTotalPage = form.totalPage.value;

// 是否是数字
		var pattern = /^\d+$/;
		if (!pattern.exec(strJumpPage)) {
			alert('请输入大于0的跳转页（整数）！');
			form.jumpPage.select();
			return false;
		} else {
			var pat = /^[0]+\d*$/;
			if (pat.exec(strJumpPage)) {
				alert('请不要输入以零开头的数字！');
				form.jumpPage.select();
				return false;
			}
		}

		// 是否大于0，小于总页数
		var iPage = parseInt(strJumpPage);
		var iTotal = parseInt(strTotalPage);
		if (iPage > 0) {
			if (iPage > iTotal) {
				msg = "请输入小于总页数" + strTotalPage + "的页码！";
				alert(msg);
				form.jumpPage.select();
				return false;
			}
		} else {
			alert('请输入大于0的页码！');
			form.jumpPage.select();
			return false;
		}
       }

       /*alert(formatNumber(0,''));
	alert(formatNumber(12432.21,'#,###'));
	alert(formatNumber(12432.21,'#,###.000#'));
	alert(formatNumber(12432,'#,###.00'));
	alert(formatNumber(12432.419,'#,###.0#'));
	*/
      function myRound(a_Num , a_Bit)
      {
            return( Math.round(a_Num * Math.pow (10 , a_Bit)) / Math.pow(10 , a_Bit)) ;
      }
      function myFormat(a_Num,a_Bit)
      {
            var temp='';
            var pointIndex=0;
            var tempLength=0;
            var tempData=0;
            var k;

            tempData=myRound(a_Num , a_Bit) ;
            //a_Num=

            temp=tempData.toString();
            pointIndex=temp.indexOf('.')
            tempLength=temp.length;
            if (pointIndex == -1)
            {
                    temp+='.';
                    k=1;
             }
             else
             {
             k=tempLength - pointInde;
             }
            for (i=k;i<=a_Bit ;i++)
            {

                    temp+='0';
            }
            return temp;
      }

