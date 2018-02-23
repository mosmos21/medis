import { Injectable } from '@angular/core';

@Injectable()
export class ConvertDateService {

  constructor() { }

  time2Date(time: number): string {
    var d = new Date( time );
    var year  = d.getFullYear();
    var month = d.getMonth() + 1;
    var day  = d.getDate();
    var hour = ( d.getHours()   < 10 ) ? '0' + d.getHours()   : d.getHours();
    var min  = ( d.getMinutes() < 10 ) ? '0' + d.getMinutes() : d.getMinutes();
    return ( year + '/' + month + '/' + day + ' ' + hour + ':' + min );
  }

  time2DateSec(time: number): string {
    var d = new Date( time * 1000 );
    var year  = d.getFullYear();
    var month = d.getMonth() + 1;
    var day  = d.getDate();
    var hour = ( d.getHours()   < 10 ) ? '0' + d.getHours()   : d.getHours();
    var min  = ( d.getMinutes() < 10 ) ? '0' + d.getMinutes() : d.getMinutes();
    var sec   = ( d.getSeconds() < 10 ) ? '0' + d.getSeconds() : d.getSeconds();
    return ( year + '/' + month + '/' + day + ' ' + hour + ':' + min + ':' + sec );
  }

}
