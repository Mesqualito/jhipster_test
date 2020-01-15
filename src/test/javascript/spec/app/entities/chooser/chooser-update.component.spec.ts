import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RadiobuttonsTestModule } from '../../../test.module';
import { ChooserUpdateComponent } from 'app/entities/chooser/chooser-update.component';
import { ChooserService } from 'app/entities/chooser/chooser.service';
import { Chooser } from 'app/shared/model/chooser.model';

describe('Component Tests', () => {
  describe('Chooser Management Update Component', () => {
    let comp: ChooserUpdateComponent;
    let fixture: ComponentFixture<ChooserUpdateComponent>;
    let service: ChooserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RadiobuttonsTestModule],
        declarations: [ChooserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChooserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChooserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChooserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Chooser(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Chooser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
