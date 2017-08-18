import {AbstractControl} from "@angular/forms";
export class PasswordValidators {
  static RepeatPassword(AC: AbstractControl) {
    let password = AC.get('password').value;
    let repeatPassword = AC.get('repeatPassword').value;
    if(password != repeatPassword) {
      console.log('wrong password');
      AC.get('repeatPassword').setErrors({RepeatPassword: true});
    } else {
      console.log('correct password');
      return null;
    }
  }
}
