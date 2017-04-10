(function() {
    'use strict';

    angular
        .module('app')
        .controller('EncaissementDialogController', EncaissementDialogController);

    EncaissementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Encaissement','Caisse'];

    function EncaissementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Encaissement ,Caisse) {
        var vm = this;

        vm.encaissement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.caisses = Caisse.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.encaissement.id !== null) {
                Encaissement.update(vm.encaissement, onSaveSuccess, onSaveError);
            } else {
                Encaissement.save(vm.encaissement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:encaissementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateMouvement = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
