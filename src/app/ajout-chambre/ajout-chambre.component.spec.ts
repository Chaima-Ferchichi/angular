import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutChambreComponent } from './ajout-chambre.component';

describe('AjoutChambreComponent', () => {
  let component: AjoutChambreComponent;
  let fixture: ComponentFixture<AjoutChambreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AjoutChambreComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjoutChambreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
