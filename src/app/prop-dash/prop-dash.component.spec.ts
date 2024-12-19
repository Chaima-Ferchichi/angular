import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropDashComponent } from './prop-dash.component';

describe('PropDashComponent', () => {
  let component: PropDashComponent;
  let fixture: ComponentFixture<PropDashComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PropDashComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropDashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
