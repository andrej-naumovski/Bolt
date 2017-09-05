import {Component, Input, OnInit} from '@angular/core';
import {ChatGroup} from "../../shared/models/chat.group";

@Component({
  selector: 'your-groups',
  templateUrl: './your-groups.component.html',
  styleUrls: ['./your-groups.component.css']
})
export class YourGroupsComponent implements OnInit {
  private _group: ChatGroup;

  @Input()
  public set group(group: ChatGroup) {
    this._group = group;
  }

  public get group() {
    return this._group;
  }

  constructor() { }

  ngOnInit() {
  }

}
