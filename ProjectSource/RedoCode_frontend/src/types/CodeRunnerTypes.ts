


enum CodeRunnerType {
    CPP_RUNNER,
    JS_RUNNER,
    UNIDENTIFIED,
  }
  type LanguageName= "cpp"|"js"|"none";

interface languageDropDownType{
    label: LanguageName,
    value: CodeRunnerType
}



export default CodeRunnerType;
export {languageDropDownType,LanguageName};
