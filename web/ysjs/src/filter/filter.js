import Moment from 'moment';
export default {
    dateFormat(val){
        if(val){
            return Moment(val).format('YYYY-MM-DD HH:mm:ss')
        } else {
            return '-'
        }
    }
}