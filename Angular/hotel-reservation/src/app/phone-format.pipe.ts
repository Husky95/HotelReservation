import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'phoneFormat'
})
export class PhoneFormatPipe implements PipeTransform {

    transform(phone: number) {
        let phoneFormat = (''+ phone).split('')
        phoneFormat.splice(6, 0, '-')
        phoneFormat.splice(3, 0, ') ')
        phoneFormat.unshift('(')
        return phoneFormat.join('');
      }

}
