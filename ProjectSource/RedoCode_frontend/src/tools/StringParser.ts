/// <reference lib="es2021" />
namespace StringParser {
  export function parseStringToHtml(txt: string): string {
    txt = txt.replace(/\n/g, '<br>')
    return txt
  }

  export function parseStringToCodeResult(txt:string): string
  {
    if(txt==="")
      return "";
      const lineBegin=">>    ";

      const lineBeginning=lineBegin+txt.replaceAll("\n","\n"+lineBegin);
      return parseStringToHtml(lineBeginning);
  }
}



export default StringParser
