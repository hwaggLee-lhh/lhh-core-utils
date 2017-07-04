//格式化


/**
 * 将文件大小转换成 ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],单位
 * @param bytes
 * @returns
 */
function bytesToSize(bytes) {
    if (bytes === 0 || bytes == null || bytes =='' || bytes == undefined) return '0 B';
    var k = 1000, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

