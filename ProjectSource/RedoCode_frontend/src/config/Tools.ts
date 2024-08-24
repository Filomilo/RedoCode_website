export const formatToHtml = (txt: string) => {
  if (txt == null) return ''
  return txt.replace('\n', '<br>')
}
