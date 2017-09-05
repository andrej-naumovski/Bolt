import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendedGroupsComponent } from './recommended-groups.component';

describe('RecommendedGroupsComponent', () => {
  let component: RecommendedGroupsComponent;
  let fixture: ComponentFixture<RecommendedGroupsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecommendedGroupsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecommendedGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
