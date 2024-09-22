namespace StringParser {
  export function parseStringToHtml(txt: string): string {
    txt = txt.replace(/\n/g, '<br>');
   return txt
  }
}

export default StringParser
