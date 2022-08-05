import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllHotelsPageComponent } from './all-hotels-page.component';

describe('AllHotelsPageComponent', () => {
  let component: AllHotelsPageComponent;
  let fixture: ComponentFixture<AllHotelsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllHotelsPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllHotelsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
