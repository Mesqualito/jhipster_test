import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChooser } from 'app/shared/model/chooser.model';

type EntityResponseType = HttpResponse<IChooser>;
type EntityArrayResponseType = HttpResponse<IChooser[]>;

@Injectable({ providedIn: 'root' })
export class ChooserService {
  public resourceUrl = SERVER_API_URL + 'api/choosers';

  constructor(protected http: HttpClient) {}

  create(chooser: IChooser): Observable<EntityResponseType> {
    return this.http.post<IChooser>(this.resourceUrl, chooser, { observe: 'response' });
  }

  update(chooser: IChooser): Observable<EntityResponseType> {
    return this.http.put<IChooser>(this.resourceUrl, chooser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChooser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChooser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
